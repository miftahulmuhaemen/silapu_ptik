package com.unlam.developerstudentclub.silapu.Utils;
import java.util.ArrayList;
import java.util.List;

public class ImplicitlyListenerComposite implements Implictly {

    @Override
    public void onRegisterActivityResponse(Boolean text) {
        for(Implictly implictly : registeredListeners){
            implictly.onRegisterActivityResponse(text);
        }
    }

    private List<Implictly> registeredListeners = new ArrayList<Implictly>();

    public void registerListener (Implictly listener) {
        registeredListeners.add(listener);
    }

    public void removeListener () {
        registeredListeners = null;
    }

}
