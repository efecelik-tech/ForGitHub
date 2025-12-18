/*package com.example.pathfindingvisualizeragorithm;

import android.content.Context;//uygulamanın ortam bilgilerine erişir(kaynaklar,temalar vb.)
import android.graphics.Canvas;//düzlemin kendisi diyebilirz tuval kağıt gibi
import android.graphics.Color;//Renk kodlarını(kırmızı,mavi,Hex kodlarını)yönetir.
import android.graphics.Paint;//esasen fırça ayarlarıdır(kalem rengi,kalınlığı,stili,iç dolu/boş vb.)
import android.util.AttributeSet;//XML dosyasındaki(activity_main.xml) ayarları (genişlik,yükseklik)okur
import android.view.MotionEvent;//Ekran üzerindeki hareketleri algılar
import android.view.View;//Her görselin atasıdır yani oluşumu için gerekli diyebiliriz.

import java.util.Random;//rastgele son için ekledik
import java.util.List;

public class PathfindingView extends View {
    //ızgara boyutu
    private Node[][]grid;//Node matrisi
    private int rows=50;//satır sayısı
    private int cols=30;//sütun sayısı
    private float cellSize;
    //Boya kalemleri aslında paintin nesneleri
    private Paint wallPaint;//duvar rengi(siyah)
    private Paint emptyPaint;//yol rengi(beyaz)
    private Paint linePaint;//çizgilerin rengi(gri)
    private Paint startPaint;//başlangıç noktası(yeşil)
    private  Paint endPaint;//hedef nokta(kırmızı)
    //BAŞLANGIŞ VE BİTİŞ NOKTALARI
    private Node startNode;
    private Node endNode;
    private List<Node> path;
    private Paint pathPaint;
    //Android bu sınıfı yaparken burayı çalıştırır.
public PathfindingView(Context context,AttributeSet attrs){
super(context,attrs);
initPaints();//kalemleri hazırla
initGrid();//kareleri oluştur
}
private void initPaints(){
wallPaint=new Paint();
wallPaint.setColor(Color.BLACK);//Bu şekilde duvarları siyah yapıyoruz
    emptyPaint=new Paint();
    emptyPaint.setColor(Color.WHITE);//yollar beyaz olsun
    linePaint=new Paint();
    linePaint.setColor(Color.GRAY);//çizgileri gri olsun
linePaint.setStyle(Paint.Style.STROKE);//çerçeveyi çiz
    linePaint.setStrokeWidth(2f);//çizgi kalınlığı
    startPaint=new Paint();
    startPaint.setColor(Color.GREEN);//başlangıç rengi yeşil olsun
    endPaint=new Paint();
    endPaint.setColor(Color.RED);//hedef rengi kırmızı olsun
    pathPaint = new Paint();
    pathPaint.setColor(Color.BLUE); // Yol MAVİ olsun
    pathPaint.setAlpha(150); // Hafif şeffaf olsun (isteğe bağlı)
}
private void initGrid(){
    grid=new Node[rows][cols];
    for(int i=0;i<rows;i++){
        for(int j=0;j<cols;j++){
            grid[i][j]=new Node(j,i);//javada satır sütun array formda ama nodelerde sütun satır yazılırmış o yüzden böyle.
        }
    }
    startNode=grid[0][0];//başlangıçta ilk satır ilk sütun
    generateRandomEndNode();

        }
        public void generateRandomEndNode(){
            Random random=new Random();
int r;
int c;
do {
    r = random.nextInt(rows);//ızgaranın boyutuna göre sayı üretir
    c = random.nextInt(cols);
}while(grid[r][c]==startNode);//başlangç üzerine gelmesin diye kontrol ediyoruz eğer whiledeki çıkarsa do ya bida dönüp yeni randleme yapıcak.
            endNode=grid[r][c];//**

}
@Override
    protected void onDraw(Canvas canvas){//android için protected olması önemliymiş diye protected yaptık.On draw androidte otomatik olarak var.
super.onDraw(canvas);
    if (getWidth() == 0) return; // Hata önleyici

    cellSize = (float) getWidth() / cols; // Genişliğe göre kare boyutu
// satır satır sütun sütun bütün ızgarayı geziyoruz.
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            Node current = grid[i][j];

            // Koordinat hesaplama
            float left = j * cellSize;
            float top = i * cellSize;
            float right = left + cellSize;
            float bottom = top + cellSize;

            // Boyama İşlemi
            if (current == startNode) {//yeşil
                canvas.drawRect(left, top, right, bottom, startPaint);
            } else if (current == endNode) {//kırmızı
                canvas.drawRect(left, top, right, bottom, endPaint);
                // 👇 BURAYA EKLE: Eğer kare, hesaplanan yolun içindeyse MAVİ boya
            }else if (path != null && path.contains(current)) {
                    canvas.drawRect(left, top, right, bottom, pathPaint);
            } else if (current.isWall) {//siyah
                canvas.drawRect(left, top, right, bottom, wallPaint);
            } else {
                canvas.drawRect(left, top, right, bottom, emptyPaint);//beyaz
            }

            // Çizgiler için gri çizgi
            canvas.drawRect(left, top, right, bottom, linePaint);
        }
    }
}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            int c = (int) (event.getX() / cellSize);
            int r = (int) (event.getY() / cellSize);

            // "rows" ve "cols" değişkenlerini kullandığımız için taşma hatası olmaz
            if (r >= 0 && r < rows && c >= 0 && c < cols) {
                Node target = grid[r][c];
                if (target != startNode && target != endNode) {
                    target.isWall = true;
                    invalidate();
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
    // MainActivity yolu hesaplayınca bu metodu çağırıp yolu gönderecek
    public void setPath(List<Node> path) {
        this.path = path;
        invalidate(); // Ekranı "geçersiz" kıl ki onDraw tekrar çalışıp mavi yolu çizsin
    }

    // MainActivity'nin algoritma için verilere erişmesini sağlayanlar:
    public Node[][] getGrid() { return grid; }
    public Node getStartNode() { return startNode; }
    public Node getEndNode() { return endNode; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    //sonradan reset koymadımı fark ettim ve reset yapıcı etot ekledim
    // MainActivity'deki "TEMİZLE" butonuna basınca burası çalışacak.
    public void reset() {
        initGrid();   // 1. Izgarayı baştan yarat (Duvarlar silinir, hedef değişir)
        path = null;  // 2. Eski mavi yolu hafızadan sil
        invalidate(); // 3. Ekranı yenile (Bembeyaz temiz sayfa aç)
    }
}
*/
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