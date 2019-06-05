package test.choxsu.model;

import com.jfinal.plugin.activerecord.Model;

public class TaxPayExcel extends Model<TaxPayExcel> {

    public String getId() {
        return get("id");
    }

    public void setId(String id) {
        set("id", id);
    }
}
