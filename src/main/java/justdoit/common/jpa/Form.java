package justdoit.common.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form {

    private Map<String, String[]> values = new HashMap<>();
    private List<String> errors = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public Map<String, String[]> getValues() {
        return values;
    }

    public void setValues(Map<String, String[]> values) {
        this.values = values;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    //</editor-fold>
}
