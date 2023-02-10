package Model.Adt;

import java.util.HashMap;

public class LatchTable implements ILatchTable{
        private HashMap<Integer, Integer> latchTable;
        private int freeLocation = 0;

        public LatchTable() {
            this.latchTable = new HashMap<>();
        }

        @Override
        public void put(int key, int Value) {
            this.latchTable.put(key, Value);
        }

        @Override
        public int get(int key) {
            return this.latchTable.get(key);
        }

        @Override
        public boolean containsKey(int key) {
            return this.latchTable.get(key) != null;
        }

        @Override
        public void update(int key, int Value) {
            this.latchTable.put(key, Value);
        }

        @Override
        public void setContent(HashMap<Integer, Integer> latchTable) {
            this.latchTable = latchTable;
        }

        @Override
        public HashMap<Integer, Integer> getContent() {
            return latchTable;
        }

        @Override
        public int getFreeValue() {
            freeLocation++;
            return freeLocation;
        }
}
