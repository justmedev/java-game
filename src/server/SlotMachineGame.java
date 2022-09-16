package server;

import game.SlotMachine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SlotMachineGame {
    public ArrayList<ArrayList<SlotMachine.SlotMachineSymbol>> slots;

    public SlotMachineGame() {
    }

    public ArrayList<SlotMachine.SlotMachineSymbol> getAllPossibleSymbols() {
        ArrayList<SlotMachine.SlotMachineSymbol> allSlots = new ArrayList<>();
        Collections.addAll(allSlots, SlotMachine.SlotMachineSymbol.values());
        return allSlots;
    }

    public ArrayList<ArrayList<SlotMachine.SlotMachineSymbol>> getSlots(int rows, int cols) {
        Random random = new Random();
        ArrayList<ArrayList<SlotMachine.SlotMachineSymbol>> slots = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            ArrayList<SlotMachine.SlotMachineSymbol> possibleSymbols = getAllPossibleSymbols();
            slots.add(new ArrayList<>());

            for (int colIndex = 0; colIndex < cols; colIndex++) {
                int randIndex = random.nextInt(possibleSymbols.size());
                SlotMachine.SlotMachineSymbol symbol = possibleSymbols.get(randIndex);
                slots.get(rowIndex).add(symbol);
                possibleSymbols.remove(symbol);
            }
        }

        return slots;
    }
}