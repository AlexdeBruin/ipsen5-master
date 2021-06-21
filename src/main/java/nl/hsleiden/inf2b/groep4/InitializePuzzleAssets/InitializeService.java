package nl.hsleiden.inf2b.groep4.InitializePuzzleAssets;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by VincentSpijkers on 23/05/2018.
 */
@Singleton
public class InitializeService {

    InitializeDAO doa;

    @Inject
    public InitializeService(InitializeDAO doa) {
        this.doa = doa;
    }

    public void insertData(){
        this.doa.insertData();
    }
}
