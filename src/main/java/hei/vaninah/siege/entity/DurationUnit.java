package hei.vaninah.siege.entity;

public enum DurationUnit {
    SECONDS {
        @Override
        public int convertFromMinute(double minutes) {
            return (int) Math.round(minutes * 60);
        }
    },
    MINUTES {
        @Override
        public int convertFromMinute(double minutes) {
            return (int) Math.round(minutes);
        }
    },
    HOURS {
        @Override
        public int convertFromMinute(double minutes) {
            return (int) Math.round(minutes / 60);
        }
    };

    public abstract int convertFromMinute(double minutes);
}
