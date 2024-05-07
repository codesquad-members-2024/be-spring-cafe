package codesquad.springcafe.util;

public class PageRequest {
    private final Long number;
    private final int size;
    private final int buttonCount;


    public PageRequest(Long number, int size, int buttonCount) {
        this.number = number;
        this.size = size;
        this.buttonCount = buttonCount;
    }

    public Long getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public Long getSkip() {
        return (number-1)*size;
    }

    public int getButtonCount() {
        return buttonCount;
    }
}
