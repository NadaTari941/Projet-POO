package appli_poo;
public class ComboBoxItem {
    private Long id;
    private String name;

    public ComboBoxItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
