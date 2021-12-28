package xyz.stasiak.cashfx.account;

public enum AccountTypeReadModel {
    BASIC {
        @Override
        public String getWithdrawCost() {
            return "5";
        }

        @Override
        public String getLoanInterestRate() {
            return "5%";
        }

        @Override
        public String getTransferCost() {
            return "10";
        }

        @Override
        public String getDepositInterestRate() {
            return "1%";
        }
    },

    BRONZE {
        @Override
        public String getWithdrawCost() {
            return "0";
        }

        @Override
        public String getLoanInterestRate() {
            return "5%";
        }

        @Override
        public String getTransferCost() {
            return "10";
        }

        @Override
        public String getDepositInterestRate() {
            return "1%";
        }
    },

    SILVER {
        @Override
        public String getWithdrawCost() {
            return "0";
        }

        @Override
        public String getLoanInterestRate() {
            return "2%";
        }

        @Override
        public String getTransferCost() {
            return "0";
        }

        @Override
        public String getDepositInterestRate() {
            return "1%";
        }
    },

    GOLD {
        @Override
        public String getWithdrawCost() {
            return "0";
        }

        @Override
        public String getLoanInterestRate() {
            return "2%";
        }

        @Override
        public String getTransferCost() {
            return "0";
        }

        @Override
        public String getDepositInterestRate() {
            return "5%";
        }
    },

    DIAMOND {
        @Override
        public String getWithdrawCost() {
            return "0";
        }

        @Override
        public String getLoanInterestRate() {
            return "1%";
        }

        @Override
        public String getTransferCost() {
            return "0";
        }

        @Override
        public String getDepositInterestRate() {
            return "10%";
        }
    };

    public abstract String getWithdrawCost();

    public abstract String getLoanInterestRate();

    public abstract String getTransferCost();

    public abstract String getDepositInterestRate();
}
