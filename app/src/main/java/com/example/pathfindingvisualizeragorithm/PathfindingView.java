
package com.example.pathfindingvisualizeragorithm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;

public class PathfindingView extends View {
    private Node[][] grid;
    private int rows = 40;
    private int cols = 30;
    private float cellSize;

    private Paint wallPaint, emptyPaint, linePaint, startPaint, endPaint, pathPaint, visitedPaint;
    private Node startNode, endNode;

    public PathfindingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
        initGrid();
    }

    private void initPaints() {
        wallPaint = new Paint(); wallPaint.setColor(Color.BLACK);
        emptyPaint = new Paint(); emptyPaint.setColor(Color.WHITE);
        linePaint = new Paint(); linePaint.setColor(Color.LTGRAY); linePaint.setStyle(Paint.Style.STROKE);
        startPaint = new Paint(); startPaint.setColor(Color.GREEN);
        endPaint = new Paint(); endPaint.setColor(Color.RED);

        pathPaint = new Paint();
        pathPaint.setColor(Color.BLUE); // Yol Mavi

        visitedPaint = new Paint();
        visitedPaint.setColor(Color.argb(150, 255, 235, 59)); // Tarananlar SARI
    }

    private void initGrid() {
        grid = new Node[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Node(j, i);//javada satır sütun array formda ama nodelerde sütun satır yazılırmış o yüzden böyle çünkü x y koordinat düzlemine göredirler.
            }
        }
        startNode = grid[0][0]; // Sol üst köşe sabit
        generateRandomEndNode(); // Hedef rastgele
    }

    // --- RASTGELE HEDEF BELİRLEME ---
    public void generateRandomEndNode() {
        Random random = new Random();
        int r, c;
        do {
            r = random.nextInt(rows);
            c = random.nextInt(cols);
        } while (grid[r][c] == startNode);
        endNode = grid[r][c];
        invalidate();
    }

    // --- TEMİZLİK METOTLARI ---

    // TAM SIFIRLAMA (KIRMIZI BUTON) - Hedefin yeri değişir!
    public void reset() {
        // 1. Izgarayı sıfırla
        grid = new Node[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Node(j, i);
            }
        }
        // 2. Başlangıcı koy
        startNode = grid[0][0];

        // 3. YENİ RASTGELE HEDEF BELİRLE
        generateRandomEndNode();

        invalidate();
    }

    // SADECE YOLU SİL (Duvarlar ve Hedef sabit)
    public void resetPathData() {
        for(int i=0; i<rows; i++) for(int j=0; j<cols; j++) grid[i][j].reset();
        invalidate();
    }

    public Node[][] getGrid() { return grid; }
    public Node getStartNode() { return startNode; }
    public Node getEndNode() { return endNode; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0) return;
        cellSize = (float) getWidth() / cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Node n = grid[i][j];
                float l = j * cellSize, t = i * cellSize, r = l + cellSize, b = t + cellSize;

                // --- Çizim Öncelik Sırası ---
                if (n == startNode) canvas.drawRect(l, t, r, b, startPaint);
                else if (n == endNode) canvas.drawRect(l, t, r, b, endPaint);
                else if (n.isWall) canvas.drawRect(l, t, r, b, wallPaint);
                else if (n.isPath) canvas.drawRect(l, t, r, b, pathPaint); // MAVİ
                else if (n.isVisited) canvas.drawRect(l, t, r, b, visitedPaint); // SARI
                else canvas.drawRect(l, t, r, b, emptyPaint);

                canvas.drawRect(l, t, r, b, linePaint);
            }
        }
    }

    @Override//bunu yaparak on touch eventin kurallarını ben yazıycam diyorum
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            int c = (int) (event.getX() / cellSize);
            int r = (int) (event.getY() / cellSize);
            if (r >= 0 && r < rows && c >= 0 && c < cols) {
                if (grid[r][c] != startNode && grid[r][c] != endNode) {
                    grid[r][c].isWall = true;
                    invalidate();//androdin viewveyi tekrar çizmesini sağlar
                }
            }
            return true;//bunu yapsın gerçekleştirmiş olsun diye
        }
        return super.onTouchEvent(event);//Android’in varsayılan davranışını çalıştırır olmanayan durumlarıda eklememk için
    }
}
