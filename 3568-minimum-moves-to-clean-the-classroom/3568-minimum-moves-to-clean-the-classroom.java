class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int sx = 0, sy = 0;
        int count = 0;

        // Find starting point and count litter
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (classroom[i].charAt(j) == 'S') {
                    sx = i;
                    sy = j;
                }
                if (classroom[i].charAt(j) == 'L') {
                    count++;
                }
            }
        }

        if (count == 0)
            return 0;

        // Give each litter a number
        int[][] id = new int[m][n];
        int num = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (classroom[i].charAt(j) == 'L') {
                    id[i][j] = num++;
                }
            }
        }

        // visited[row][column][energy][mask]
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << count];

        // Queue: row, column, energy, mask, moves
        java.util.Queue<int[]> q = new java.util.LinkedList<>();

        int startMask = (1 << count) - 1;

        q.add(new int[]{sx, sy, energy, startMask, 0});

        visited[sx][sy][energy][startMask] = true;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!q.isEmpty()) {

            int[] cur = q.poll();

            int x = cur[0];
            int y = cur[1];
            int e = cur[2];
            int mask = cur[3];
            int moves = cur[4];

            // All litter collected
            if (mask == 0)
                return moves;

            // Cannot move without energy
            if (e == 0)
                continue;

            for (int d = 0; d < 4; d++) {

                int nx = x + dx[d];
                int ny = y + dy[d];

                // Outside grid
                if (nx < 0 || nx >= m || ny < 0 || ny >= n)
                    continue;

                char cell = classroom[nx].charAt(ny);

                // Obstacle
                if (cell == 'X')
                    continue;

                int newEnergy = e - 1;

                // Reset area
                if (cell == 'R')
                    newEnergy = energy;

                int newMask = mask;

                // Collect litter
                if (cell == 'L') {
                    newMask = mask & ~(1 << id[nx][ny]);
                }

                if (!visited[nx][ny][newEnergy][newMask]) {

                    visited[nx][ny][newEnergy][newMask] = true;

                    q.add(new int[]{
                        nx, ny, newEnergy, newMask, moves + 1
                    });
                }
            }
        }

        return -1;
    }
}