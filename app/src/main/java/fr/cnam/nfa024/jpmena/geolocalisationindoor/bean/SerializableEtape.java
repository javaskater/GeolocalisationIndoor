package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

import java.io.Serializable;

import fr.cnam.nfa024.jpmena.geolocalisationindoor.EtapeActivity;

public class SerializableEtape implements Serializable {
    private SerializableSalle mSalleFrom;
    private SerializableSalle mSalleTo;
    private SerializableMouvement mMouvement;
    private Boolean mEtapeTerminee;

    public SerializableSalle getmSalleFrom() {
        return mSalleFrom;
    }

    public void setmSalleFrom(SerializableSalle mSalleFrom) {
        this.mSalleFrom = mSalleFrom;
    }

    public SerializableSalle getmSalleTo() {
        return mSalleTo;
    }

    public void setmSalleTo(SerializableSalle mSalleTo) {
        this.mSalleTo = mSalleTo;
    }

    public SerializableMouvement getmMouvement() {
        return mMouvement;
    }

    public void setmMouvement(SerializableMouvement mMouvement) {
        this.mMouvement = mMouvement;
    }

    public Boolean getmEtapeTerminee() {
        return mEtapeTerminee;
    }

    public void setmEtapeTerminee(Boolean mEtapeTerminee) {
        this.mEtapeTerminee = mEtapeTerminee;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof SerializableEtape)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        SerializableEtape c = (SerializableEtape) o;

        // Compare the data members and return accordingly
        return Integer.compare(mSalleFrom.getIdentifiant(), c.getmSalleFrom().getIdentifiant()) == 0
                && Integer.compare(mSalleTo.getIdentifiant(), c.getmSalleTo().getIdentifiant()) == 0
                && mMouvement.getDeplacement().equalsIgnoreCase(c.getmMouvement().getDeplacement());
    }
}
