package dataModel;

import java.util.Objects;

public class LesVorm {

        private final boolean IS_BOVEN_DE_21;
        private final Lesduur LESDUUR;
        private final boolean HEEFT_OM_DE_WEEK_LES;

        public LesVorm(boolean IS_BOVEN_DE_21, Lesduur LESDUUR, boolean HEEFT_OM_DE_WEEK_LES) {
            this.IS_BOVEN_DE_21 = IS_BOVEN_DE_21;
            this.LESDUUR = LESDUUR;
            this.HEEFT_OM_DE_WEEK_LES = HEEFT_OM_DE_WEEK_LES;
        }

    public boolean HEEFT_OM_DE_WEEK_LES() {
        return HEEFT_OM_DE_WEEK_LES;
    }

    public boolean IS_BOVEN_DE_21() {
        return IS_BOVEN_DE_21;
    }

    @Override
    public String toString() {
            String s = "Lesvorm: ";

        s += IS_BOVEN_DE_21 ? "boven de 21, " : "onder de 21, ";
        s += LESDUUR + ",";
        s += HEEFT_OM_DE_WEEK_LES ? " om de week." : " elke week.";

        return s;
    }

    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LesVorm lesVorm = (LesVorm) o;
            return IS_BOVEN_DE_21 == lesVorm.IS_BOVEN_DE_21 &&
                    HEEFT_OM_DE_WEEK_LES == lesVorm.HEEFT_OM_DE_WEEK_LES &&
                    LESDUUR == lesVorm.LESDUUR;
        }

        @Override
        public int hashCode() {
            return Objects.hash(IS_BOVEN_DE_21, LESDUUR, HEEFT_OM_DE_WEEK_LES);
        }
}

