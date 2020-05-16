package userinterface;

import impresario.IModel;

import java.io.IOException;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model) throws IOException {
		if(viewName.equals("ScoutSelectionScreenView") == true)
		{
			return new ScoutSelectionScreenView(model);
		}
		else if(viewName.equals("TreeLotCoordinatorView") == true)
		{
			System.out.println("In View Factory Tree Lot coordinator view");
			return new TreeLotCoordinatorView(model);
		}
		else if(viewName.equals("ScoutSearchView") == true)
		{
			System.out.println("In View Factory Search Scout fxml view");
			return new ScoutSearchView(model);
		}
		else if(viewName.equals("UpdateScoutView") == true)
		{
			return new UpdateScoutView(model);
		}
		else if(viewName.equals("RegisterScoutView") == true)
		{
			System.out.println("In View Factory Register Scout View");
			return new RegisterScoutView(model);
		}
		else if(viewName.equals("TreeTypeSearchView") == true)
		{
			return new TreeTypeSearchView(model);
		}
		else if(viewName.equals("UpdateTreeTypeView") == true)
		{
			return new UpdateTreeTypeView(model);
		}
		else if(viewName.equals("TreeTypeSelectionScreenView") == true)
		{
			return new TreeTypeSelectionScreenView(model);
		}
		else if(viewName.equals("RegisterTreeTypeView") == true)
		{
			return new RegisterTreeTypeView(model);
		}
		else if(viewName.equals("RegisterTreeView") == true)
		{
			return new RegisterTreeView(model);
		}
		else if(viewName.equals("TreeSelectionScreenView") == true)
		{
			return new TreeSelectionScreenView(model);
		}
		else if(viewName.equals("UpdateTreeView") == true)
		{
			return new UpdateTreeView(model);
		}
		else if(viewName.equals("TreeSearchView") == true)
		{
			return new TreeSearchView(model);
		}
		else if(viewName.equals("DeleteATreeView") == true)
		{
			return new DeleteATreeView(model);
		}
		else if(viewName.equals("DeleteATreePopupView") == true)
		{
			return new DeleteATreePopupView(model);
		}
		else if(viewName.equals("OpenShiftView") == true)
		{
			System.out.println("openshiftview");
			return new OpenShiftView(model);
		}
		else if(viewName.equals("OpenShiftSelectScoutsView") == true){
			return new OpenShiftSelectScoutsView(model);
		}
		else if (viewName.equals("SellTreeView")== true)
		{
			return new SellTreeView(model);
		}
		else if (viewName.equals("SellingConfirmationView")== true)
		{
			return new SellingConfirmationView(model);
		}
		else if(viewName.equals("ThankYouView")==true)
		{
			return new ThankYouView(model);
		}
		else if(viewName.equals("OpenShiftSelectScoutsView") == true)
		{
			return new OpenShiftSelectScoutsView(model);
		}
		else if(viewName.equals("CloseAShiftView") == true)
		{
			return new CloseAShiftView(model);
		}
		else
			return null;
	}
}
