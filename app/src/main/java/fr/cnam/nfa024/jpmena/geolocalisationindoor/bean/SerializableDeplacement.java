package fr.cnam.nfa024.jpmena.geolocalisationindoor.bean;

import java.io.Serializable;

public class SerializableDeplacement implements Serializable {
    private Integer mPosition;
    private String mDeplacement;
    private Boolean mFait;

    public Integer getmPosition() {
        return mPosition;
    }

    public void setmPosition(Integer mPosition) {
        this.mPosition = mPosition;
    }

    public String getmDeplacement() {
        return mDeplacement;
    }

    public void setmDeplacement(String mDeplacement) {
        this.mDeplacement = mDeplacement;
    }

    public Boolean getmFait() {
        return mFait;
    }

    public void setmFait(Boolean mFait) {
        this.mFait = mFait;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof SerializableDeplacement)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        SerializableDeplacement c = (SerializableDeplacement) o;

        // Compare the data members and return accordingly
        return Integer.compare(mPosition, c.getmPosition()) == 0
                && mDeplacement.equalsIgnoreCase(c.getmDeplacement());
    }
}
