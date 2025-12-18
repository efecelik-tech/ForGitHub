/*package com.example.pathfindingvisualizeragorithm; // Paket ismin

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PathfindingView pathfindingView;
    private TextView tvStats;
    private Button btnRunBFS;
    private Button btnReset; // Yeni butonu buraya tanıttım
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // XML dosyasını buraya bağlıyoruz

        pathfindingView = findViewById(R.id.pathfindingView);
        tvStats = findViewById(R.id.tvStats);
        btnRunBFS = findViewById(R.id.btnRunBFS);
        btnReset = findViewById(R.id.btnReset); //  XML'deki kırmızı butonu bulduk
        btnRunBFS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBFSAlgorithm();
            }
        });
        // 3. DEĞİŞİKLİK: TEMİZLE BUTONU TIKLAMA OLAYI
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // View içindeki reset metodunu çağır (Ekranı siler)
                pathfindingView.reset();

                // Üstteki yazı alanını sıfırla
                tvStats.setText("Harita Temizlendi.\nYeni duvarlar çizip tekrar başlatabilirsiniz.");
            }
        });
    }

    private void runBFSAlgorithm() {
        BFSAlgorithm bfs = new BFSAlgorithm();
        Node[][] grid = pathfindingView.getGrid();
        Node startNode = pathfindingView.getStartNode();
        Node endNode = pathfindingView.getEndNode();
        int rows = pathfindingView.getRows();
        int cols = pathfindingView.getCols();

        List<Node> path = bfs.solve(grid, startNode, endNode, rows, cols);

        if (path != null && !path.isEmpty()) {
            pathfindingView.setPath(path);
            String statText = "SONUÇLAR:\n" +
                    "⏱ Süre: " + (bfs.executionTime / 1000) + " µs\n" +
                    "🔍 Ziyaret Edilen: " + bfs.nodesVisited + " kare\n" +
                    "👣 Yol Uzunluğu: " + path.size() + " adım";
            tvStats.setText(statText);
        } else {
            tvStats.setText(" HEDEF BULUNAMADI!");
            Toast.makeText(this, "Hedefe ulaşılamıyor!", Toast.LENGTH_SHORT).show();
        }
    }
}

 */
package com.example.pathfindingvisualizeragorithm;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {//Eski Android sürümleriyle uyumlu (Compatible) modern özellikler sunan bir aktivite türüdür,Ayrıca "Toolbar", "Action Bar", "Material Design" gibi özellikleri kullanabilmeni sağlar

    private PathfindingView pathfindingView;
    private TextView tvStats;
    private PathfindingService pathfindingService;

    @Override//appcpmpat activityde onCreate var eğer ben bunu yanlış yazarsam beni uyarır,aslında ben bunu kendime göre özelleştirdiğim için kullanıyorum
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Android'de bir aktivite (ekran) oluşturulurken arka planda yapılması gereken yüzlerce karmaşık işlemi pencereyi açmak, sistemi hazırlamak, hafıza ayırmak vb.Önce standart Android hazırlığını yap, sonra benim kodlarımı çalıştır demektir.
        setContentView(R.layout.activity_main);

        pathfindingView = findViewById(R.id.pathfindingView);
        tvStats = findViewById(R.id.tvStats); // XML'deki TextView ID'si
        pathfindingService = new PathfindingService();

        Button btnAStar = findViewById(R.id.btnRunAStar);
        Button btnDijkstra = findViewById(R.id.btnRunDijkstra);
        Button btnRunBFS = findViewById(R.id.btnRunBFS);
        Button btnReset = findViewById(R.id.btnReset);

        // 1. A* (MANHATTAN)
        btnAStar.setOnClickListener(v -> runHeuristicAlgo(PathfindingService.AlgorithmType.ASTAR_MANHATTAN));

        // 2. DIJKSTRA
        btnDijkstra.setOnClickListener(v -> runHeuristicAlgo(PathfindingService.AlgorithmType.DIJKSTRA));

        //3.BFS
        btnRunBFS.setOnClickListener(v -> {
            pathfindingView.resetPathData();

            BFSAlgorithm bfs = new BFSAlgorithm();
            List<Node> path = bfs.solve(
                    pathfindingView.getGrid(),
                    pathfindingView.getStartNode(),
                    pathfindingView.getEndNode()
            );

            pathfindingView.invalidate(); // Çizimi güncelle

            if (path != null && !path.isEmpty()) {
                // SİZE ÖZEL STATİSTİK GÖSTERİMİ
                String statText = "BFS SONUÇLARI:\n" +
                        " Süre: " + (bfs.executionTime / 1_000_000.0) + " ms\n" +
                        " Ziyaret: " + bfs.nodesVisited + " kare\n" +
                        " Yol: " + path.size() + " birim";
                tvStats.setText(statText);
            } else {
                tvStats.setText("BFS: Hedef Bulunamadı!");
            }
        });

        // 4. RESET (HER ŞEYİ SİL + HEDEFİ RASTGELE YAP)
        btnReset.setOnClickListener(v -> {
            pathfindingView.reset();
            tvStats.setText("Harita Temizlendi.\nYeni hedef belirlendi.");
        });
    }

    private void runHeuristicAlgo(PathfindingService.AlgorithmType type) {
        pathfindingView.resetPathData();
        PathfindingService.AlgorithmResult result = pathfindingService.runPathfinding(
                pathfindingView.getGrid(),
                pathfindingView.getStartNode(),
                pathfindingView.getEndNode(),
                type
        );
        pathfindingView.invalidate(); // Çizimi güncelle

        if (result.path != null) {
            // A* VE DIJKSTRA İÇİN STATİSTİK
            String statText = type.name() + " SONUÇLARI:\n" +
                    " Süre: " + (result.durationNs / 1_000_000.0) + " ms\n" +
                    " Ziyaret: " + result.visitedNodeCount + " kare\n" +
                    " Yol: " + result.path.size() + " birim";
            tvStats.setText(statText);
        } else {
            tvStats.setText(type.name() + ": Yol Bulunamadı!");
        }
    }
}