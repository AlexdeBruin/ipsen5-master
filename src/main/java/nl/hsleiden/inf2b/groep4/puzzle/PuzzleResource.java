package nl.hsleiden.inf2b.groep4.puzzle;

import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.inf2b.groep4.account.Account;
import nl.hsleiden.inf2b.groep4.account.AccountService;
import nl.hsleiden.inf2b.groep4.cost_card.CostCard;
import nl.hsleiden.inf2b.groep4.interpreter.Interpreter;
import nl.hsleiden.inf2b.groep4.puzzle.block.BackgroundBlock;
import nl.hsleiden.inf2b.groep4.puzzle.block.ForgroundBlock;
import nl.hsleiden.inf2b.groep4.puzzle.block.Hero;
import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;
import nl.hsleiden.inf2b.groep4.solution.Solution;
import nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO;

import com.google.inject.Inject;
import nl.hsleiden.inf2b.groep4.solution.SolutionService;

import javax.annotation.security.RolesAllowed;
import javax.validation.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonMap;

@Singleton
@Path("/puzzle")
@Produces(MediaType.APPLICATION_JSON)
public class PuzzleResource {

    private PuzzleService puzzleService;
    private AccountService accountService;
    private SolutionService solutionService;


    @Inject
    public PuzzleResource(PuzzleService puzzleService, AccountService accountService, SolutionService solutionService) {
        this.puzzleService = puzzleService;
        this.accountService = accountService;
        this.solutionService = solutionService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    @UnitOfWork
    @RolesAllowed("GROUP")
    public Response savePuzzle(PuzzleCode puzzleCode, @Auth Account account) {
        if (nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("production") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<CostCard>> violations = validator.validate(puzzleCode.getPuzzle().getCostCard());
            if (violations.size() > 0) {
                List<String> errors = new ArrayList<>();
                errors.add("De kostekaart is niet valide");
                return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(singletonMap("errors", errors)).build();

            }

            if (puzzleCode.code == null) {
                List<String> errors = new ArrayList<>();
                errors.add("Je hebt geen code meegestuurd");
                return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(singletonMap("errors", errors)).build();
            }

            if (account.getPuzzle() != null) {
                List<String> errors = new ArrayList<>();
                errors.add("Je hebt je puzzel al verstuurd");
                return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(singletonMap("errors", errors)).build();

            }
            List errors = puzzleService.savePuzzle(puzzleCode.puzzle);
            Puzzle localPuzzle = null;

            if (errors.size() == 0) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(puzzleCode.puzzle);
                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    localPuzzle = (Puzzle) ois.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).build();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if (localPuzzle == null) {
                    return null;
                }
                Interpreter interpreter = new Interpreter(new Solution(puzzleCode.code, localPuzzle));
                Solution solution = interpreter.start();
                if (solution.isSucces()) {
                    account.setPuzzle(puzzleCode.puzzle);
                    solution.setAccount(account);
                    solution.setPuzzle(puzzleCode.puzzle);
                    solutionService.saveSolutionForMaker(solution);
                    accountService.updateAccount(account);
                }
            }
            return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(singletonMap("errors", errors)).build();
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

    //zandbak en productie
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/test")
	@RolesAllowed("GROUP")
	public Response getSolutionTest(PuzzleCode puzzleCode) {
        if (nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("production") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed") || nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("sandbox") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            //Allows the group of students to test their code, doesn't send the code to the database.
            Response response = puzzleService.getTestSolution(puzzleCode.getPuzzle(), puzzleCode.getCode());
        return response;
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

    //Zandbak
    @POST
    @Path("/{id}/sandbox")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public Solution getSandboxSolution(@PathParam("id") int id, String code) {
        if (nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("sandbox") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            return puzzleService.getSandboxSolution(id, code);
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Zandbak status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

    //zandbak maken puzzle alleen admin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create/sandbox")
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public Response saveSandboxPuzzle(PuzzleCode puzzleCode) {
        puzzleCode.puzzle.setSandbox(true);
        List errors = puzzleService.savePuzzle(puzzleCode.puzzle);
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(singletonMap("errors", errors)).build();

    }

//zowel zandbak als productie
    @GET
    @Path("/{id}")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public Puzzle getPuzzle(@PathParam("id") int id) {
        if (!nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            return puzzleService.getPuzzle(id);
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

    @GET
    @Path("/allForgroundBlocks")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public List<ForgroundBlock> getAllForgroundBlocks() {
        if(nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("production") || nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("sandbox")){
            return puzzleService.getAllForgroundBlocks();
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }

    }

    @GET
    @Path("/allBackgroundBlocks")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public List<BackgroundBlock> getAllBackgroundBlocks() {
        if(StatusDAO.getStatusModel().getStatus().equals("production") || nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("sandbox")){
            return puzzleService.getAllBackgroundBlocks();
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

    @GET
    @Path("/allHeroes")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public List<Hero> getHeroes() {
        if(nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("production") || nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("sandbox")){
            return puzzleService.getAllHeroes();
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

    //production
    @POST
    @Path("/{id}/solution")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public Solution getSolution(@PathParam("id") int id, String code, @Auth Account auth) {
        if (nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("production") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            return puzzleService.getSolution(id, code, auth);
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

    //Production
    @GET
    @Path("/{id}/bestSolution")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public Solution getBestSolution(@PathParam("id") int id, @Auth Account auth) {
        if (nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("production") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed") || nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("sandbox") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            return puzzleService.getBestSolution(id, auth);
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

    //production
    @GET
    @Path("/{id}/attempts")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public int getAttempts(@PathParam("id") int id, @Auth Account auth) {
        if (nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("production") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            return puzzleService.getAttempts(id, auth);
        } else {
            throw new WebApplicationException(
                "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                Response.Status.UNAUTHORIZED
        );
    }
    }

    //production
    @GET
    @Path("/puzzleList")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public PuzzleListModel getPuzzleList(@Auth Account auth) {
        if (nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("production") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            return puzzleService.getPuzzleList(auth.getAccountId());
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }

    }

    //production
    @GET
    @Path("/mysolution")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public Puzzle getMySolution(@Auth Account account) {
        if (nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("production") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            Puzzle puzzle = account.getPuzzle();
            if (puzzle == null) {
                return null;
            }
            return puzzle;
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in Production status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

    //zandbak
    @GET
    @Path("/sandboxList")
    @UnitOfWork
	@RolesAllowed("GROUP")
	public List<Integer> getSandboxList() {
        if (nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("sandbox") && !nl.hsleiden.inf2b.groep4.environmentStatus.StatusDAO.getStatusModel().getStatus().equals("closed")) {
            return puzzleService.getSandboxList();
        } else {
            throw new WebApplicationException(
                    "Not Available at the moment. If you are currently in zandbak status please ask admin to change the environment status.",
                    Response.Status.UNAUTHORIZED
            );
        }
    }

}