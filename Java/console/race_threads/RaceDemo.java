public class RaceDemo {

    private final static int NUMRUNNERS = 3;

    public static void main(String[] args) {
        SelfishRunner[] runners = new SelfishRunner[NUMRUNNERS];

        for (int i = 0; i < NUMRUNNERS; i++) {
            runners[i] = new SelfishRunner(i);
//            runners[i].setPriority(2);
        }

		runners[0].setPriority(1);
		runners[1].setPriority(10);
		runners[2].setPriority(1);
		
        for (int i = 0; i < NUMRUNNERS; i++)
            runners[i].start();
    }
}
