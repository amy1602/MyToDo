package com.example.thinha.todo;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ThiNha on 10/5/2016.
 */
public class ViewDialog {

    TextView detailContent;
    TextView detailTime;
    TextView detailDate;
    Button btnEdit;
    Button btnDelete;
    int itemIndex;
    Dialog dialog;
    public ViewDialog(int i)
    {
        itemIndex = i;
    }

    public void showDialog(final Activity activity){
            dialog  = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.detail_item);

        detailContent= (TextView)dialog.findViewById(R.id.detailContent);
        detailTime = (TextView)dialog.findViewById(R.id.detailTime);
        detailDate= (TextView)dialog.findViewById(R.id.detailDate);
        btnEdit= (Button)dialog.findViewById(R.id.buttonEdit);
        btnDelete = (Button)dialog.findViewById(R.id.buttonDelete);
            dialogContentInit(itemIndex);
            dialog.show();
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    dialog.dismiss();
                }

                return false;
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                                MainActivity.arrayHour.remove(itemIndex);
                                MainActivity.arrayMinute.remove(itemIndex);
                                MainActivity.arraySecond.remove(itemIndex);
                                MainActivity.arrayDate.remove(itemIndex);
                                MainActivity.arrayMonth.remove(itemIndex);
                                MainActivity.arrayYear.remove(itemIndex);

                                MainActivity.nameArrayList.remove(itemIndex);
                                MainActivity.arrayAdapter.notifyDataSetChanged();

                                MainActivity.mydb.deleteContact(MainActivity.arrayItemID.get(itemIndex));
                                MainActivity.arrayItemID.remove(itemIndex);
                                MainActivity.id--;

                            }

                        })

                        .setNegativeButton("No", null)
                        .show();

                dialog.cancel();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EditItemActivity.class);
                intent.putExtra("ItemID",itemIndex);
                intent.putExtra("checkBtn",0);
                activity.startActivity(intent);
                dialog.cancel();
            }
        });



        }

        private void dialogContentInit(int i)
        {

            detailContent.setText(MainActivity.nameArrayList.get(itemIndex).toString());
            detailTime.setText(MainActivity.arrayHour.get(itemIndex).toString() + " : " + MainActivity.arrayMinute.get(itemIndex).toString()+" : "+MainActivity.arraySecond.get(itemIndex).toString());
            detailDate.setText(MainActivity.arrayDate.get(itemIndex).toString() + " / " + MainActivity.arrayMonth.get(itemIndex).toString()+" / "+MainActivity.arrayYear.get(itemIndex).toString());
        }


        public void cancel()
        {
            dialog.cancel();
        }



}
