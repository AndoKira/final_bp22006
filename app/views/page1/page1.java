package app.views.page1;

import app.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Random;

@PageTitle("じゃんけんゲーム")
@Route(value = "じぇんけん", layout = MainLayout.class)
//@Push
public class page1 extends  VerticalLayout{

    private int te = 0;
    int randamValue;
    Span jankenSPan=new Span();
    Span resultSpan=new Span("判定");

    public String janken() {
        Random random = new Random();
        randamValue = random.nextInt(3);
        if (randamValue == 0) return "✊";
        else if (randamValue == 1) return "✋";
        else return "✌";
    }

    public String Judge(int enemy) {
        if (te == enemy) return "引き分け";
        else if (te == 0 && enemy == 1 || te == 1 && enemy == 2 || te == 2 && enemy == 0) {
            return "負け";
        } else {
            return "勝ち";
        }
    }

    public void updateJanken() {
        jankenSPan.setText(janken());
    }
    public void updateresult(int randamValue){
        resultSpan.setText(Judge(randamValue));
    }
    public page1() {
        Button gu = new Button("✊");
        gu.addClickListener(e -> {
            te = 0;
            updateJanken();
            updateresult(randamValue);
        });

        Button pa = new Button("✋");
        pa.addClickListener(e -> {
            te = 1;
            updateJanken();
            updateresult(randamValue);
        });

        Button tyoki = new Button("✌");
        tyoki.addClickListener(e -> {
            te = 2;
            updateJanken();
            updateresult(randamValue);
        });

        setMargin(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(jankenSPan, new HorizontalLayout(gu, tyoki, pa), resultSpan);
    }
}








