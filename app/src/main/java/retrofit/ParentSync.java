package retrofit;

import android.util.Log;

import java.util.List;
import model.Parent;

public class ParentSync {

    public List<Parent> parents;
    public static Parent parentList;

    public List<Parent> getParents() {

        Log.d("ID: ",String.valueOf(parents.get(0).getIdParent()));
        Log.d("TAMANHO: ",String.valueOf(parents.size()));
        return parents;
    }
}
