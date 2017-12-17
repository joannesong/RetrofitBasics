package nyc.c4q.jsonretro;

import java.util.List;

/**
 * Created by joannesong on 12/17/17.
 */

public class Colors {

    private String color;
    private String category;
    private String type;
    private Code code;

    public String getColor() {
        return color;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public Code getCode() {
        return code;
    }

    private class Code {
        private List<Integer> rgba;
        private String hex;


        public List<Integer> getRgba() {
            return rgba;
        }

        public String getHex() {
            return hex;
        }
    }
}
