package se.elnazhonar.sample.android.testing;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class EndToEndTest {

	private static final String PACKAGE_NAME = "se.elnazhonar.sample";

	private static final int LAUNCH_TIMEOUT = 5000;
	private static final int CONNECTION_TIMEOUT = 30000;
	private static final int UI_TIMEOUT = 5000;


	private UiDevice mDevice;

	@Before
	public void startMainActivityFromHomeScreen() {
		// Initialize UiDevice instance
		mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

		try {
			if (!mDevice.isScreenOn()) {
				mDevice.wakeUp();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		// Start from the home screen
		mDevice.pressHome();

		// Wait for launcher
		final String launcherPackage = getLauncherPackageName();
		assertThat(launcherPackage, notNullValue());
		mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

		// Launch the app
		Context context = InstrumentationRegistry.getContext();
		final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
		context.startActivity(intent);

		// Wait for the app to appear
		mDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
	}

	@Test
	public void checkPreconditions() {
		assertThat(mDevice, notNullValue());
	}

	@Test
	public void testSearchForFoodWillUpdateSearchResult() throws UiObjectNotFoundException {
		String searchKey = "pizza";
		String expectedFood = "Pizza pollo";

		UiObject searchResultObject = searchForFood(searchKey, expectedFood);
		assertNotNull(searchResultObject);

	}

	@Test
	public void testClickOnSearchResultOpenFoodDetails() throws UiObjectNotFoundException {
		String searchKey = "tiramisu";
		String expectedFood = "Tiramisu Latte";

		UiObject searchResultObject = searchForFood(searchKey, expectedFood);
		assertNotNull(searchResultObject);

		searchResultObject.click();

		UiObject2 toolbar = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "toolbar_widget")), UI_TIMEOUT);
		assertEquals(toolbar.findObject(By.clazz(TextView.class)).getText(), "Nutrition Facts");
		UiObject2 favoriteMenuItem = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "menu_favorite")), UI_TIMEOUT);
		assertNotNull(favoriteMenuItem);
		UiObject2 foodTitle = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "food_title")), UI_TIMEOUT);
		assertNotNull(foodTitle);
		assertTrue(expectedFood.contains(foodTitle.getText()));
		UiObject2 foodCaloriesValue = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "food_calories_value")), UI_TIMEOUT);
		assertNotNull(foodCaloriesValue);
		UiObject2 foodProteinValue = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "food_protein_value")), UI_TIMEOUT);
		assertNotNull(foodProteinValue);
		UiObject2 foodCarbohydrateValue = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "food_carbohydrate_value")), UI_TIMEOUT);
		assertNotNull(foodCarbohydrateValue);
	}

	@Test
	public void testClickOnSavedFoodWillOpenFoodDetails() throws UiObjectNotFoundException {
		String searchKey = "keso";
		String expectedFood = "Arla mini keso";

		UiObject searchResultObject = searchForFood(searchKey, expectedFood);
		assertNotNull(searchResultObject);

		assertNotNull(searchResultObject);
		UiObject favoriteButton = searchResultObject.getChild(new UiSelector().className(ImageButton.class));
		assertNotNull(favoriteButton);
		if (favoriteButton.getContentDescription().equals("is favorite")) {
			favoriteButton.click();
		}
		favoriteButton.click();
		assertEquals(favoriteButton.getContentDescription(), "is favorite");

		mDevice.pressBack();

		UiObject2 toolbar = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "toolbar_widget")), UI_TIMEOUT);
		assertEquals(toolbar.findObject(By.clazz(TextView.class)).getText(), "My Saved Foods");

		UiObject favoriteFood = scrollGridToFind(expectedFood);

		favoriteFood.click();
		UiObject2 foodDetailsToolbar = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "toolbar_widget")), UI_TIMEOUT);
		assertEquals(foodDetailsToolbar.findObject(By.clazz(TextView.class)).getText(), "Nutrition Facts");
		UiObject2 favoriteMenuItem = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "menu_favorite")), UI_TIMEOUT);
		assertNotNull(favoriteMenuItem);
		UiObject2 foodTitle = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "food_title")), UI_TIMEOUT);
		assertNotNull(foodTitle);
		assertTrue(expectedFood.contains(foodTitle.getText()));

	}

	@Test
	public void testMarkSearchResultAsFavoriteWillAddFavoriteFoodToSavedFoodList() throws UiObjectNotFoundException {
		String searchKey = "vapiano";
		String expectedFood = "Nizza Salat";

		UiObject searchResultObject = searchForFood(searchKey, expectedFood);
		assertNotNull(searchResultObject);
		UiObject favoriteButton = searchResultObject.getChild(new UiSelector().className(ImageButton.class));
		assertNotNull(favoriteButton);
		if (favoriteButton.getContentDescription().equals("is favorite")) {
			favoriteButton.click();
		}
		assertEquals(favoriteButton.getContentDescription(), "is not favorite");
		favoriteButton.click();
		assertEquals(favoriteButton.getContentDescription(), "is favorite");

		searchResultObject.click();

		UiObject2 favoriteMenuItem = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "menu_favorite")), UI_TIMEOUT);
		assertNotNull(favoriteMenuItem);
		assertEquals(favoriteMenuItem.getContentDescription(), "is favorite");

		mDevice.pressBack();
		mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "menu_favorite")), UI_TIMEOUT);
		assertEquals(favoriteButton.getContentDescription(), "is favorite");

		mDevice.pressBack();

		UiObject2 toolbar = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "toolbar_widget")), UI_TIMEOUT);
		assertEquals(toolbar.findObject(By.clazz(TextView.class)).getText(), "My Saved Foods");

		UiObject favoriteFood = scrollGridToFind(expectedFood);
		assertTrue(expectedFood.contains(favoriteFood.getContentDescription()));


	}

	@Test
	public void testMarkFavoriteFoodAsNonFavoriteFromDetailsViewWillRemoveFoodFromSavedFoodList() throws UiObjectNotFoundException {
		String searchKey = "vapiano";
		String expectedFood = "Vapiano Pasta Carbonara";

		UiObject searchResultObject = searchForFood(searchKey, expectedFood);
		assertNotNull(searchResultObject);
		UiObject favoriteButton = searchResultObject.getChild(new UiSelector().className(ImageButton.class));
		assertNotNull(favoriteButton);
		if (favoriteButton.getContentDescription().equals("is favorite")) {
			favoriteButton.click();
		}
		assertEquals(favoriteButton.getContentDescription(), "is not favorite");
		favoriteButton.click();
		assertEquals(favoriteButton.getContentDescription(), "is favorite");

		searchResultObject.click();

		UiObject2 favoriteMenuItem = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "menu_favorite")), UI_TIMEOUT);
		assertNotNull(favoriteMenuItem);
		assertEquals(favoriteMenuItem.getContentDescription(), "is favorite");

		favoriteMenuItem.click();
		mDevice.pressBack();
		mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "menu_favorite")), UI_TIMEOUT);

		assertEquals(favoriteButton.getContentDescription(), "is not favorite");

		mDevice.pressBack();

		UiObject2 toolbar = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "toolbar_widget")), UI_TIMEOUT);
		assertEquals(toolbar.findObject(By.clazz(TextView.class)).getText(), "My Saved Foods");

		try {
			scrollGridToFind(expectedFood);
		} catch (Exception expectedException) {
			assertEquals(expectedException.getMessage(), expectedFood + " was not found!");
		}
	}

	private UiObject searchForFood(String searchKey, String expectedFood) throws UiObjectNotFoundException {
		mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "menu_search")), UI_TIMEOUT).click();
		UiObject2 searchTxt = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "search_src_text")), UI_TIMEOUT);

		searchTxt.setText(searchKey);
		mDevice.pressEnter();

		UiObject2 searchResult = mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "search_recycler_view")), CONNECTION_TIMEOUT);
		assertNotNull(searchResult);

		UiSelector uiSelector = new UiSelector().className(TextView.class.getName()).textStartsWith(expectedFood);
		UiScrollable scrollable = new UiScrollable(new UiSelector().className(android.support.v7.widget.RecyclerView.class.getName()));
		UiObject searchCandidate;
		if (scrollable.scrollIntoView(uiSelector)) {
			searchCandidate = scrollable.getChildByText(new UiSelector().className(RelativeLayout.class.getName()), expectedFood, true);
			return searchCandidate;
		} else {
			throw new UiObjectNotFoundException(expectedFood + " was not found!");
		}
	}

	private UiObject scrollGridToFind(String foodName) throws UiObjectNotFoundException {
		mDevice.wait(Until.findObject(By.res(PACKAGE_NAME, "categories")), UI_TIMEOUT);

		UiSelector uiSelector = new UiSelector().descriptionContains(foodName);
		if (mDevice.findObject(uiSelector).exists()) {
			return mDevice.findObject(uiSelector);
		}
		UiScrollable scrollableGrid = new UiScrollable(new UiSelector().className(android.widget.GridView.class.getName()));
		if (scrollableGrid.scrollIntoView(uiSelector)) {
			return scrollableGrid.getChild(uiSelector);
		} else {
			throw new UiObjectNotFoundException(foodName + " was not found!");
		}
	}

	private String getLauncherPackageName() {
		// Create launcher Intent
		final Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);

		// Use PackageManager to get the launcher package name
		PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
		ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return resolveInfo.activityInfo.packageName;
	}
}