import java.util.SplittableRandom;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinArraySum {

    private static final int SIZE = 1_000_000;
    private static final int MIN_CHUNK = 20;

    public static void main(String[] args) {
        int[] arr = new int[SIZE];

        // 0..100 (включно)
        SplittableRandom rnd = new SplittableRandom();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt(0, 101);
        }

        ForkJoinPool pool = ForkJoinPool.commonPool();

        long start = System.nanoTime();
        long sum = pool.invoke(new SumTask(arr, 0, arr.length));
        long end = System.nanoTime();

        System.out.println("Array size: " + SIZE);
        System.out.println("Min chunk: " + MIN_CHUNK);
        System.out.println("Sum: " + sum);
        System.out.printf("Time: %.2f ms%n", (end - start) / 1_000_000.0);
    }

    // Рекурсивна задача: ділить на 2 частини, доки відрізок не стане < 20
    static class SumTask extends RecursiveTask<Long> {
        private final int[] arr;
        private final int from; // inclusive
        private final int to;   // exclusive

        SumTask(int[] arr, int from, int to) {
            this.arr = arr;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            int len = to - from;

            if (len < MIN_CHUNK) {
                long local = 0;
                for (int i = from; i < to; i++) local += arr[i];
                return local;
            }

            int mid = from + len / 2;
            SumTask left = new SumTask(arr, from, mid);
            SumTask right = new SumTask(arr, mid, to);

            left.fork();              // запускаємо ліву частину асинхронно
            long rightRes = right.compute(); // праву рахуємо в поточному потоці
            long leftRes = left.join();      // чекаємо ліву

            return leftRes + rightRes;
        }
    }
}
