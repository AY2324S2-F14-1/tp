package brokeculator.expense;

import brokeculator.enumerators.Category;
import brokeculator.frontend.UI;
import brokeculator.storage.parsing.FileKeyword;
import brokeculator.storage.parsing.SaveableType;

import java.time.LocalDateTime;

import java.util.ArrayList;

public class ExpenseManager {
    private final ArrayList<Expense> expenses;

    public ExpenseManager() {
        expenses = new ArrayList<>();
    }

    public ExpenseManager(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public String add(Expense expense) {
        if (expense.getCategory() != null && !Category.isValidCategory(expense.getCategory())) {
            return "Category does not exist";
        }
        expenses.add(expense);
        return "Added expense: " + expense;
    }


    public boolean isExpenseIndexValid(int index) {
        return index >= 1 && index <= expenses.size();
    }

    public Expense getExpense(int index) {
        if (!isExpenseIndexValid(index)) {
            return null;
        }
        return expenses.get(index - 1);
    }

    public void delete(int index) {
        if (!isExpenseIndexValid(index)) {
            return;
        }
        expenses.remove(index - 1);
    }

    public double summariseExpenses(String description, LocalDateTime date, String category,
                                    int beginIndex, int endIndex) {
        double total = 0;
        if (endIndex == -1 || endIndex >= expenses.size()) {
            endIndex = expenses.size() - 1;
        }
        boolean isDescriptionNull = (description == null);
        ArrayList<Expense> expensesToSummarise = new ArrayList<Expense>();
        for (Expense expense : expenses.subList(beginIndex, endIndex + 1)) {
            if (!isDescriptionNull && !expense.getDescription().contains(description)) {
                continue;
            }
            // TODO implement date processing
            // TODO implement category processing
            expensesToSummarise.add(expense);
        }
        for (Expense expense : expensesToSummarise) {
            total += expense.getAmount();
        }
        if (expensesToSummarise.isEmpty()) {
            UI.prettyPrint("Nothing to summarise!");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < expensesToSummarise.size(); i++) {
                sb.append(i + 1).append(". ").append(expensesToSummarise.get(i)).append(System.lineSeparator());
            }
            String summarisedExpensesListString = String.valueOf(sb);
            UI.prettyPrint(summarisedExpensesListString);
        }
        return total;
    }

    public ArrayList<Expense> listExpenses(int amountToList) {
        if (amountToList == -1) {
            return expenses;
        } else {
            return new ArrayList<>(expenses.subList(0, Math.min(amountToList, expenses.size())));
        }
    }

    public String getExpensesListString(int beginIndex, int endIndex) {
        assert !this.expenses.isEmpty();

        int lastIdxToPrint;
        if (endIndex < 0 || endIndex > this.expenses.size()) {
            lastIdxToPrint = this.expenses.size();
        } else {
            lastIdxToPrint = endIndex;
        }

        int firstIdxToPrint;
        if (beginIndex < 0) {
            firstIdxToPrint = 0;
        } else {
            firstIdxToPrint = beginIndex;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = firstIdxToPrint; i < lastIdxToPrint; i++) {
            sb.append(i + 1).append(". ").append(expenses.get(i)).append(System.lineSeparator());
        }

        return sb.toString();
    }

    public String getExpensesStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (Expense expense : expenses) {
            String currentExpenseString = expense.getStringRepresentation();
            String finalExpenseString = FileKeyword.formatWithKeyword(SaveableType.EXPENSE, currentExpenseString);
            sb.append(finalExpenseString);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getExpensesListString(0, expenses.size());
    }
    
    public int getNumberOfExpensesTracked() {
        return expenses.size();
    }
}
