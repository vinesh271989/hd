Feature: Validate the home depot website

Scenario: Breadcrumb validation
Given As a user, I am on Homedepot.ca home page 
When I hover on "Shop by Department" And then hover on "Bath"
And I click on "Vanity Tops"
And I click on "Sort By : Recommended" dropdown
And Select "Top Sellers" option
Then I verify "Top Sellers" is displayed in place of "Recommended"
And I verify the verbiage "In stock Today at" and Selected Store Name is displayed
And I verify the lowest level in the breadcrumb matches the title on the current page as "Vanity Tops"


Scenario: Cart validation
Given As a user, I am on Homedepot.ca home page 
When I hover on "Shop by Department" And "Bath"
And I click on "Vanity Tops"
And I click on "Add to Cart" for any product displayed
Then I verify the Add to cart Panel slides from right
And I verify the message "1 Item has been added to your cart" displayed on the Panel
And I verify the Order Subtotal Price matches the Price of the product
And I click on "View Cart" button on the panel
And I verify I am on the Cart Page and Change the Quantity of the product to 4
And I verify the price is multiplied accordingly

