package csci422.lwm.mathtutor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class NumQuestionsDialogFragment extends DialogFragment
{	
	/** The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * The position of the selected element is provided.
     */
    public interface QuestionsDialogListener
    {
        public void onDialogPositiveClick(int position);
    }
    
    // Use this instance of the interface to deliver action events
   QuestionsDialogListener numQuestionsListener;
   
   // Override the Fragment.onAttach() method to instantiate the QuestionsDialogListener
   @Override
   public void onAttach(Activity activity)
   {
       super.onAttach(activity);
       // Verify that the host activity implements the callback interface
       try
       {
           // Instantiate the numQuestionsListener so we can send events to the host
           numQuestionsListener = (QuestionsDialogListener) activity;
       }
       catch (ClassCastException e)
       {
           // The activity doesn't implement the interface, throw exception
           throw new ClassCastException(activity.toString() + " must implement QuestionsDialogListener");
       }
   }
   
   /** This is the OK button listener for the alert dialog,
    *  which in turn invokes the method onPositiveClick(position)
    *  of the hosting activity which is supposed to implement it
   */
   OnClickListener positiveListener = new OnClickListener()
   {
       @Override
       public void onClick(DialogInterface dialog, int which)
       {
           AlertDialog alert = (AlertDialog) dialog;
           int position = alert.getListView().getCheckedItemPosition();
           numQuestionsListener.onDialogPositiveClick(Integer.valueOf(getResources().getStringArray(R.array.numberOfQuizProblems)[position]));
       }
   };
   
   /** This is a callback method which will be executed
    *  on creating this fragment
    */
   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState)
   {

       /** Getting the arguments passed to this fragment */
       Bundle bundle = getArguments();
       int position = bundle.getInt("position");

       /** Creating a builder for the alert dialog window */
       AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

       /** Setting a title for the window */
       b.setTitle(R.string.dialog_quiz_number_of_probs);

       /** Setting items to the alert dialog */
       b.setSingleChoiceItems(R.array.numberOfQuizProblems, position, null);

       /** Setting a positive button and its listener */
       b.setPositiveButton(R.string.dialog_quiz_start, positiveListener);

       /** Setting a positive button and its listener */
       b.setNegativeButton(R.string.dialog_quiz_cancel, null);

       /** Creating the alert dialog window using the builder class */
       AlertDialog d = b.create();

       /** Return the alert dialog window */
       return d;
   }
}
