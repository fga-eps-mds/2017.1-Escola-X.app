package retrofit;

import android.util.Log;

import java.util.List;
import model.Parent;

public class ParentSync {

    public List<Parent> parents;
    public static Parent parentList;

    public List<Parent> getParents() {

        for(int aux = 0; aux < parents.size();aux ++) {
            Log.d("ID: ",String.valueOf(parents.get(aux).getIdParent()));
            Log.d("TAMANHO: ",String.valueOf(parents.size()));
        }


        return parents;
    }
}
