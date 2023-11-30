package day11;

public enum Operation {

    ADD {
        @Override
        public Long apply(Long a, Long b) {
            return a + b;
        }
    }, MULT {
        @Override
        public Long apply(Long a, Long b) {
            return a * b;
        }
    }, DIV {
        @Override
        public Long apply(Long a, Long b) {
            return a / b;
        }
    }, MOD {
        @Override
        public Long apply(Long a, Long b) {
            return a % b;
        }
    };

    public abstract Long apply(Long a, Long b);

}
