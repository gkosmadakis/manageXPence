package uk.co.irokottaki.moneycontrol.utils;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.model.AnyYear;

import static android.content.Context.MODE_PRIVATE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.CANCEL;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSES_FILE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OK;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SAVE;

public class CustomAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private HashMap<String, AnyYear> yearsMappedToObjectYearsMap;
    private ChartsUtil util;
    private EditText editExpenseField;

    public CustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
        util = new ChartsUtil(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
        //just return 0 if your list items do not have an Id variable.
    }

    @TargetApi(21)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.edit_results_layout, null);
            view.setBackground(context.getDrawable(R.drawable.list_divider));
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button editBtn = (Button)view.findViewById(R.id.edit_btn);
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);

        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                showInputDialogOnClickingEditBtn(list.get(position));

                notifyDataSetChanged();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                deleteAnExpense(list.get(position));
                notifyDataSetChanged();
            }
        });


        return view;
    }

    protected void showInputDialogOnClickingEditBtn(final String lineToUpdate) {

        //this method handles the popup window to edit an expense
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.edit_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog);
        alertDialogBuilder.setView(promptView);

        editExpenseField = new EditText(context);
        editExpenseField = promptView.findViewById(R.id.editField);

        editExpenseField.setText(lineToUpdate);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(SAVE, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        /* call the processEdit pass line as it was before and line as it is when the user updated the expense*/
                        processEdit(lineToUpdate, editExpenseField.getText().toString());

                    }
                })
                .setNegativeButton(CANCEL,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    private void processEdit(String lineToUpdate, String lineUpdated) {

        String amountEdited = lineUpdated.substring(0, lineUpdated.indexOf(' '));
        int lengthTillDate = lineUpdated.lastIndexOf(' ');
        String descriptionEdited = lineUpdated.substring(lineUpdated.indexOf(' '), lengthTillDate)
                .trim();
        String dateEdited = lineUpdated.substring(lengthTillDate, lineUpdated.length()).trim();

        //if the user has made changes and the edited line is more than 34 characters
        if (amountEdited.length() > 7 || descriptionEdited.length() > 15 || dateEdited
                .length() != 10) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle("Maximum input characters exceeded")
                    .setMessage("Some of the fields you edited exceed the maximum input " +
                            "characters. \n" +
                            "***Remember amount field has 7 characters limit, description has 15 " +
                            "characters limit and date must be equal to 10 characters.\n " +
                            "Please edit again your expense");
            AlertDialog alert1;
            builder.setPositiveButton(OK,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alert1 = builder.create();
            alert1.show();

        }

        // finally if the user has not exceeded the text limiters we process the Edit
        else if (amountEdited.length() <= 7 && descriptionEdited.length() <= 15 && dateEdited
                .length() == 10) {
            FileInputStream fstream = null;
            BufferedReader br = null;
            try {
                fstream = context.openFileInput(EXPENSES_FILE);
                br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;
                StringBuilder fileContent = new StringBuilder();
                String amount = "";
                String desc = "";
                String date = "";
                String formatStr = "%-8s%-15s%-10s";//formats the columns
                int lineIndex = 0;
                while ((strLine = br.readLine()) != null) {
                    lineIndex++;
                    if (strLine.replaceAll("\\s+", " ").equals(lineToUpdate.trim()) &&
                            ++lineIndex > 2) {
                        amount = lineUpdated.substring(0, lineUpdated.indexOf(' '));//prints the
                        // amount
                        int index = lineUpdated.lastIndexOf(' ');
                        desc = lineUpdated.substring(lineUpdated.indexOf(' '), index).trim();
                        //prints the description
                        date = lineUpdated.substring(index, lineUpdated.length()).trim();//prints
                        // the date
                        fileContent.append(String.format(formatStr, amount, desc, date).trim());
                        //write edited line in the file
                        fileContent.append("\r\n");//write a line
                    } else {
                        // update content as it is
                        fileContent.append(strLine);
                        fileContent.append("\r\n");
                    }
                }
                PrintWriter out = new PrintWriter(context.openFileOutput(EXPENSES_FILE, MODE_PRIVATE));
                out.write(fileContent.toString());

                out.close();
                fstream.close();

                yearsMappedToObjectYearsMap = util.readTheFile();// call to update the map
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle("Edit Expense")
                        .setMessage("The expense is successfully edited");
                builder.setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /* Not used */
                    }
                });
                builder.show();
            } catch (FileNotFoundException e) {
                Log.e("File not found ", e.toString());
            } catch (IOException e) {
                Log.e("IOException: ", e.toString());
            }
            finally {
                if (fstream!= null){
                    try {
                        fstream.close();
                    } catch (IOException e) {
                        Log.e("IOException", e.getMessage());
                    }
                }
                if (br!= null){
                    try {
                        br.close();
                    } catch (IOException e) {
                        Log.e("IOException", e.getMessage());
                    }
                }
            }
        }
    }

    private void deleteAnExpense(String lineForDeletion) {

        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        PrintWriter out = null;
        try {
            fileInputStream = context.openFileInput(EXPENSES_FILE);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;
            StringBuilder fileContent = new StringBuilder();
            while ((strLine = bufferedReader.readLine()) != null) {

                //if the line in the file is not the same with the one selected by the user
                if (!strLine.replaceAll("\\s+", " ").equals(lineForDeletion.trim())) {
                    fileContent.append(strLine);
                    fileContent.append("\r\n");//then write it to the file.
                    //So the line that will be the same as the lineForDeletion, the line selected
                    //will not be written to the file, it will be deleted.
                }
            }
            out = new PrintWriter(context.openFileOutput(EXPENSES_FILE, MODE_PRIVATE));
            if (fileContent.toString().replaceAll("\\s+", " ").equals("Amount Description Date ")) {
                out.write(fileContent.toString().trim());
                out.write("\r\n");
                out.write("\r\n");//if the expense deleted is the last one that means the file
                // contains only the header.
                out.close();//So i add the header and two lines after it.
            }
            else {
                out.write(fileContent.toString().trim());
                out.write("\r\n");//if the expense is not the last in the file i add one line
                out.close();//because the next expense is added will go straight into that line
            }

            yearsMappedToObjectYearsMap = util.readTheFile();// call to update the map
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle("Expense Deletion")
                    .setMessage("The expense is deleted");
            builder.setPositiveButton(OK, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    /* Not used */
                }
            });
            builder.show();
        } catch (Exception e) {//Catch exception if any
            Log.e("Error: ", e.getMessage());
        }
        finally {
            if (fileInputStream!= null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                }
            }
            if (bufferedReader!= null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                }
            }
            if (out!= null){
                out.close();
            }
        }
    }


}
