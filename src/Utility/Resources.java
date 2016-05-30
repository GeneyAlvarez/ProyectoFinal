package Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Necho on 24/04/2016.
 */
public class Resources {

    ArrayList<String> adapter=new ArrayList<>();
    ArrayList<String> dataStructure=new ArrayList<>();
    ArrayList<String> data=new ArrayList<>();
    ArrayList<String> row=new ArrayList<>();


    public Resources(String pack, ArrayList<String> forms){
        this.adapter=initAdapter(adapter,pack);
        this.dataStructure=initDataStructure(dataStructure,pack,forms);
        this.data=initData(data,pack);
        this.row=initRow(row,pack);
    }

    public ArrayList<String> initAdapter(ArrayList<String> adapter, String pack){
        adapter.add("package "+pack+";");
        adapter.add("");
        adapter.add("import android.content.Context;");
        adapter.add("import android.graphics.Bitmap;");
        adapter.add("import android.graphics.BitmapFactory;");
        adapter.add("import android.net.Uri;");
        adapter.add("import android.support.v7.widget.RecyclerView;");
        adapter.add("import android.view.LayoutInflater;");
        adapter.add("import android.view.View;");
        adapter.add("import android.view.ViewGroup;");
        adapter.add("import android.widget.ArrayAdapter;");
        adapter.add("import android.widget.ImageView;");
        adapter.add("import android.widget.ListView;");
        adapter.add("import android.widget.TextView;");
        adapter.add("import java.util.Collections;");
        adapter.add("import java.util.List;");
        adapter.add("");
        adapter.add("public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {");
        adapter.add("");
        adapter.add("\tprivate final Context context;");
        adapter.add("\tprivate LayoutInflater inflater;");
        adapter.add("\tprivate DataStructure data;");
        adapter.add("\tprivate RecyclerClickListner mRecyclerClickListner;");
        adapter.add("\tprivate RecyclerLClickListner mRecyclerLClickListner;");
        adapter.add("\tprivate ArrayAdapter arrayAdapter;");
        adapter.add("");
        adapter.add("\tpublic ViewAdapter(Context context){");
        adapter.add("\t\tinflater = LayoutInflater.from(context);");
        adapter.add("\t\tthis.context = context;");
        adapter.add("\t\tthis.data = DataStructure.getInstance();");
        adapter.add("\t}");
        adapter.add("");
        adapter.add("\t@Override");
        adapter.add("\tpublic MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {");
        adapter.add("\t\tView view = inflater.inflate(R.layout.custom_row, viewGroup, false);");
        adapter.add("\t\tMyViewHolder holder = new MyViewHolder(view);");
        adapter.add("\t\treturn holder;");
        adapter.add("\t}");
        adapter.add("");
        adapter.add("\t@Override");
        adapter.add("\tpublic void onBindViewHolder(MyViewHolder myViewHolder, int i) {");
        adapter.add("\t\tData dat=data.Arraytest.get(i);");
        adapter.add("\t\tRow row = dat.getRow();");
        adapter.add("\t\tmyViewHolder.tv.setText(row.texto1);");
        adapter.add("\t\tmyViewHolder.tv2.setText(row.texto2);");
        adapter.add("\t\tmyViewHolder.tv3.setText(row.texto3);");
        adapter.add("\t\tif(row.img!=null){myViewHolder.iv.setImageURI(Uri.parse(row.img));}");
        adapter.add("\t}");
        adapter.add("");
        adapter.add("\t@Override");
        adapter.add("\tpublic int getItemCount() {");
        adapter.add("\t\treturn data.Arraytest.size();");
        adapter.add("\t}");
        adapter.add("");
        adapter.add("\tpublic void setRecyclerClickListner(RecyclerClickListner recyclerClickListner){");
        adapter.add("\t\tmRecyclerClickListner = recyclerClickListner;");
        adapter.add("\t}");
        adapter.add("");
        adapter.add("\tpublic void setRecyclerLClickListner(RecyclerLClickListner recyclerLClickListner){");
        adapter.add("\t\tmRecyclerLClickListner = recyclerLClickListner;");
        adapter.add("\t}");
        adapter.add("");
        adapter.add("\tclass MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {");
        adapter.add("\t\tprivate TextView tv;");
        adapter.add("\t\tprivate TextView tv2;");
        adapter.add("\t\tprivate TextView tv3;");
        adapter.add("\t\tprivate ImageView iv;");
        adapter.add("");
        adapter.add("\t\tpublic MyViewHolder(View itemView) {");
        adapter.add("\t\t\tsuper(itemView);");
        adapter.add("\t\t\titemView.setOnClickListener(this);");
        adapter.add("\t\t\titemView.setOnLongClickListener(this);");
        adapter.add("\t\t\ttv = (TextView) itemView.findViewById(R.id.texto1);");
        adapter.add("\t\t\ttv2 = (TextView) itemView.findViewById(R.id.texto2);");
        adapter.add("\t\t\ttv3 = (TextView) itemView.findViewById(R.id.texto3);");
        adapter.add("\t\t\tiv=(ImageView)itemView.findViewById(R.id.img);");
        adapter.add("\t\t}");
        adapter.add("");
        adapter.add("\t\t@Override");
        adapter.add("\t\tpublic void onClick(View v) {");
        adapter.add("\t\t\tif (mRecyclerClickListner != null) {");
        adapter.add("\t\t\t\tmRecyclerClickListner.itemClick(v, getPosition());");
        adapter.add("\t\t\t}");
        adapter.add("\t\t}");
        adapter.add("");
        adapter.add("\t\t@Override");
        adapter.add("\t\tpublic boolean onLongClick(View v) {");
        adapter.add("\t\t\tif (mRecyclerLClickListner != null) {");
        adapter.add("\t\t\t\tmRecyclerLClickListner.itemLClick(v, getPosition());");
        adapter.add("\t\t\t}");
        adapter.add("\t\t\treturn true;");
        adapter.add("\t\t}");

        adapter.add("\t}");
        adapter.add("");
        adapter.add("\tpublic interface RecyclerClickListner{");
        adapter.add("\t\tpublic void itemClick(View view, int position);");
        adapter.add("\t}");
        adapter.add("\tpublic interface RecyclerLClickListner{");
        adapter.add("\t\tpublic void itemLClick(View view, int position);");
        adapter.add("\t}");
        adapter.add("}");

        return adapter;
    }

    public ArrayList<String> initDataStructure(ArrayList<String> array, String pack, ArrayList<String> forms){//aqui edito para agregar new DataStructure
        array.add("package "+pack+";");
        array.add("");
        array.add("import java.util.ArrayList;");
        array.add("");
        array.add("public class DataStructure {");
        array.add("\tprivate static DataStructure ourInstance =new DataStructure();");
        array.add("\tArrayList<Data> Arraytest=new ArrayList<Data>();");
        array.add("\tArrayList<Data> Temp=new ArrayList<Data>();");
        array.add("");
        array.add("\tpublic static DataStructure getInstance() { return ourInstance; }");
        array.add("");
        array.add("\tprivate DataStructure() {");
        if(forms!=null){
            for(String test: forms){
                array.add(test);
            }
        }
        array.add("");
        array.add("\t}");
        array.add("");
        array.add("\tpublic String getNameClass(Data dat){");
        array.add("\t\tObject inf = dat.getOb();");
        array.add("\t\tString class_object = \"\" + inf.getClass();");
        array.add("\t\tint piv = class_object.split(\"\\\\.\").length;");
        array.add("\t\tclass_object = class_object.split(\"\\\\.\")[piv - 1];");
        array.add("\t\treturn class_object;");
        array.add("\t}");
        array.add("");

        array.add("\tpublic void PULL(String classname){");
        array.add("\t\tArrayList<Data> ArrayResult1=new ArrayList<Data>();// NEW ARRAYTEST");
        array.add("\t\tArrayList<Data> ArrayResult2=new ArrayList<Data>();// ARRAY WITH ONLY CLASSNAME");
        array.add("\t\tfor(int i=0;i<Arraytest.size();i++){");
        array.add("\t\t\tData test=Arraytest.get(i);");
        array.add("\t\t\tString class_name=getNameClass(test);");
        array.add("\t\t\tif(class_name.equals(classname)){");
        array.add("\t\t\t\tArrayResult1.add(test);");
        array.add("\t\t\t}else{ArrayResult2.add(test);}}");
        array.add("\t\tArraytest=ArrayResult1;");
        array.add("\t\tTemp=ArrayResult2;");
        array.add("\t}");

        array.add("");
        array.add("\tpublic void PUSH(){");
        array.add("\t\tfor(Data dat : Temp){");
        array.add("\t\t\tArraytest.add(dat);");
        array.add("\t\t}");
        array.add("\t}");
        array.add("");
        array.add("}");

        return array;
    }

    public ArrayList<String> initData(ArrayList<String> array, String pack){
        array.add("package "+pack+";");
        array.add("");
        array.add("public class Data {");
        array.add("\tObject ob;");
        array.add("\tRow row;");
        array.add("");
        array.add("\tpublic Data(Object ob, Row row) {");
        array.add("\t\tthis.ob = ob;");
        array.add("\t\tthis.row = row;");
        array.add("\t}");
        array.add("");
        array.add("\tpublic Object getOb() { return ob; }\n");
        array.add("");
        array.add("\tpublic void setOb(Object ob) { this.ob = ob; }");
        array.add("");
        array.add("\tpublic Row getRow() { return row; }");
        array.add("");
        array.add("\tpublic void setRow(Row row) { this.row = row; }");
        array.add("");
        array.add("}");

        return array;
    }

    public ArrayList<String> initRow(ArrayList<String> array, String pack){
        array.add("package "+pack+";");
        array.add("");
        array.add("public class Row {");
        array.add("\tString texto1;");
        array.add("\tString texto2;");
        array.add("\tString texto3;");
        array.add("\tString img;");
        array.add("");
        array.add("\tpublic Row(String text1, String text2, String text3, String img) {");
        array.add("\t\tthis.texto1 = text1;");
        array.add("\t\tthis.texto2 = text2;");
        array.add("\t\tthis.texto3 = text3;");
        array.add("\t\tthis.img = img;");
        array.add("\t}");
        array.add("");
        array.add("\tpublic String getTexto1() { return texto1; }");
        array.add("");
        array.add("\tpublic void setTexto1(String text1) { this.texto1 = text1; }");
        array.add("");
        array.add("\tpublic String getTexto2() { return texto2; }");
        array.add("");
        array.add("\tpublic void setTexto2(String text2) {this.texto2 = text2; }");
        array.add("");
        array.add("\tpublic String getTexto3() { return texto3; }");
        array.add("");
        array.add("\tpublic void setTexto3(String text3) { this.texto3 = text3; }");
        array.add("");
        array.add("\tpublic String getImg() { return img; }");
        array.add("");
        array.add("\tpublic void setImg(String img) { this.img = img; }");
        array.add("");
        array.add("}");

        return array;
    }

    public static void WriteClass(File f, ArrayList<String> adapter) throws FileNotFoundException, UnsupportedEncodingException {
        int tam=adapter.size();

        PrintWriter writer = new PrintWriter(f, "UTF-8");
        for(int i=0;i<tam;i++){
            writer.println(adapter.get(i));
        }
        writer.close();
    }

    public ArrayList<String> getAdapter() {
        return adapter;
    }

    public ArrayList<String> getDataStructure() {

            return dataStructure;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public ArrayList<String> getRow() {
        return row;
    }
}
