package larry.germana;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Cuvant extends Translation {
    private String romana;
    private String traducere;

    public String getTraducere() {
        return traducere;
    }

    public void setRomana(String romana) {
        this.romana = romana;
    }

    public void setTraducere(String traducere) {
        this.traducere = traducere;
    }

    public String getRomana() {
        return romana;
    }

    public Cuvant(int apparition, String romana, String traducere) {
        super(apparition);
        this.romana = romana;
        this.traducere = traducere;
    }

    public Cuvant(int apparition, StringTokenizer st) {
        super(apparition);
        this.romana = st.nextToken();
        this.traducere = st.nextToken();
        if (st.hasMoreTokens()) {
            this.setDescription(st.nextToken());
        }
    }

    @Override
    public List<Translation> getTranslations() {
        List<Translation> list = new ArrayList<Translation>();
        list.add(this);
        list.add(new Cuvant(this.getApparition(), this.getTraducere(), this.getRomana()));
        return list;
    }

}
