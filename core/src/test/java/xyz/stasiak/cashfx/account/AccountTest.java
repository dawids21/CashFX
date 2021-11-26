package xyz.stasiak.cashfx.account;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import xyz.stasiak.cashfx.account.exceptions.NotEnoughMoney;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountTest {

    @Test
    void make_transfer_moves_money_from_one_account_to_the_other() {
        var fromAccount = new TestAccountBuilder().money(20).build();
        var toAccount = new TestAccountBuilder().money(0).build();

        fromAccount.makeTransfer(10, toAccount);

        assertThat(fromAccount.toReadModel().money()).isEqualTo(10);
        assertThat(toAccount.toReadModel().money()).isEqualTo(10);
    }

    @Test
    void make_transfer_should_charge_basic_account() {
        var fromAccount = new TestAccountBuilder().money(20).charge(0).type(new BasicAccountType()).build();
        var toAccount = new TestAccountBuilder().build();

        fromAccount.makeTransfer(10, toAccount);

        assertThat(fromAccount.toReadModel().charge()).isGreaterThan(0);
    }

    @Test
    void make_transfer_throws_exception_when_not_enough_money() {
        var fromAccount = new TestAccountBuilder().money(0).build();
        var toAccount = new TestAccountBuilder().build();

        assertThatThrownBy(() -> fromAccount.makeTransfer(10, toAccount))
                .isInstanceOf(NotEnoughMoney.class)
                .hasMessageContaining("10");
    }

    @Test
    void pay_charge_reduce_charge_to_zero_when_enough_money() {
        var account = new TestAccountBuilder().money(20).charge(10).build();

        account.payCharge();

        var readModel = account.toReadModel();
        assertThat(readModel.charge()).isZero();
        assertThat(readModel.money()).isEqualTo(10);
    }

    @Test
    void pay_charge_reduce_charge_as_much_as_possible() {
        var account = new TestAccountBuilder().money(10).charge(20).build();

        account.payCharge();

        var readModel = account.toReadModel();
        assertThat(readModel.charge()).isEqualTo(10);
        assertThat(readModel.money()).isZero();
    }

    @Test
    void pay_charge_does_nothing_when_no_money() {
        var account = new TestAccountBuilder().money(0).charge(10).build();

        account.payCharge();

        var readModel = account.toReadModel();
        assertThat(readModel.charge()).isEqualTo(10);
        assertThat(readModel.money()).isZero();
    }

    @Test
    void take_loan_adds_new_money_to_account() {
        var account = new TestAccountBuilder().money(0).build();

        account.takeLoan(100);

        assertThat(account.toReadModel().money()).isEqualTo(100);
    }

    @Test
    void take_loan_charge_an_account() {
        var account = new TestAccountBuilder().charge(0).build();

        account.takeLoan(100);

        assertThat(account.toReadModel().charge()).isGreaterThan(0);
    }

    @Test
    void take_loan_charge_based_on_amount() {
        var firstAccount = new TestAccountBuilder().charge(0).build();
        var secondAccount = new TestAccountBuilder().charge(0).build();

        firstAccount.takeLoan(100);
        secondAccount.takeLoan(200);

        assertThat(firstAccount.toReadModel().charge()).isLessThan(secondAccount.toReadModel().charge());
    }

    @Test
    void take_loan_charge_based_on_account_type() {
        var firstAccount = new TestAccountBuilder().charge(0).type(new BasicAccountType()).build();
        var secondAccount = new TestAccountBuilder().charge(0).type(new DiamondAccountType()).build();

        firstAccount.takeLoan(100);
        secondAccount.takeLoan(100);

        assertThat(firstAccount.toReadModel().charge()).isGreaterThan(secondAccount.toReadModel().charge());
    }

    @Test
    void withdraw_removes_money_from_account() {
        var account = new TestAccountBuilder().money(100).build();

        account.withdraw(100);

        assertThat(account.toReadModel().money()).isZero();
    }

    @Test
    void withdraw_throws_exception_when_not_enough_money() {
        var account = new TestAccountBuilder().money(0).build();

        assertThatThrownBy(() -> account.withdraw(100))
                .isInstanceOf(NotEnoughMoney.class)
                .hasMessageContaining("100");
    }

    @Test
    void withdraw_charges_basic_account() {
        var account = new TestAccountBuilder().money(100).charge(0).type(new BasicAccountType()).build();

        account.withdraw(100);

        assertThat(account.toReadModel().charge()).isGreaterThan(0);
    }

    @Test
    void withdraw_not_charges_non_basic_account() {
        var account = new TestAccountBuilder().money(100).charge(0).type(new DiamondAccountType()).build();

        account.withdraw(100);

        assertThat(account.toReadModel().charge()).isZero();
    }

    @Test
    void deposit_adds_money_to_account() {
        var account = new TestAccountBuilder().money(0).build();

        account.deposit(100);

        assertThat(account.toReadModel().money()).isEqualTo(100);
    }

    @Test
    void deposit_not_charge_any_account() {
        var account = new TestAccountBuilder().charge(0).type(new BasicAccountType()).build();

        account.deposit(100);

        assertThat(account.toReadModel().charge()).isZero();
    }

    @Test
    void open_deposit_adds_money_to_account() {
        var account = new TestAccountBuilder().money(100).build();

        account.openDeposit(100);

        assertThat(account.toReadModel().money()).isGreaterThan(100);
    }

    @Test
    void open_deposit_adds_money_based_on_amount() {
        var firstAccount = new TestAccountBuilder().money(200).build();
        var secondAccount = new TestAccountBuilder().money(200).build();

        firstAccount.openDeposit(100);
        secondAccount.openDeposit(200);

        assertThat(firstAccount.toReadModel().money()).isLessThan(secondAccount.toReadModel().money());
    }

    @Test
    void open_deposit_adds_money_based_on_account_type() {
        var firstAccount = new TestAccountBuilder().money(100).type(new BasicAccountType()).build();
        var secondAccount = new TestAccountBuilder().money(100).type(new DiamondAccountType()).build();

        firstAccount.openDeposit(100);
        secondAccount.openDeposit(100);

        assertThat(firstAccount.toReadModel().money()).isLessThan(secondAccount.toReadModel().money());
    }

    @Test
    void open_deposit_throws_exception_when_not_enough_money() {
        var account = new TestAccountBuilder().money(0).build();

        assertThatThrownBy(() -> account.openDeposit(100))
                .isInstanceOf(NotEnoughMoney.class)
                .hasMessageContaining("100");
    }
}
