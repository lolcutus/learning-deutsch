package ro.cuzma.tools.germana.translation;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public abstract class Translation {

    private String description = "";
    private int apparition = 0;

    public String getLanguage() {
        return language;
    }

    private String language;

    public void setLanguage(String language) {
        this.language = language;
    }

    public Translation(int apparition, String language) {
        this.apparition = apparition;
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getApparition() {
        return apparition;
    }

    public void setApparition(int apparition) {
        this.apparition = apparition;
    }

}
