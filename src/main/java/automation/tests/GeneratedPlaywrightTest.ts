import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

<think>
Okay, I need to convert the given Selenium Java code into Playwright TypeScript. Let me start by understanding what the original code does.

First, the Java code sets up a ChromeDriver using WebDriverManager. It then navigates to "https://html.com/tags/table/", maximizes the window, and sets an implicit wait. After that, it finds all the rows and columns in the tables on the page using XPath and prints their counts.

Now, I'll think about how to translate this into Playwright. I know that Playwright uses a different API, so I'll need to adjust the code accordingly.

I'll start by setting up a Playwright test class. Since the original code is a static main method, I'll convert it into a class with a test method. I'll use TestNG's @BeforeMethod to initialize the browser and context, similar to how the driver is set up in the Java code.

Next, I'll replace the WebDriverManager setup with Playwright's browser setup. In Playwright, I'll create a chromium browser instance and a context with a new page. I'll also set the base URL to avoid repeating the URL in each test.

For navigating to the URL, instead of driver.get(), I'll use page.goto(). I'll also maximize the page using page.setViewportSize() since Playwright doesn't have a direct maximize function, but setting the viewport size should achieve a similar effect.

The implicit wait in Selenium is handled using driver.manage().timeouts().implicitlyWait(). In Playwright, I can set the default navigation timeout via the context options, which should cover the same purpose.

To find the rows and columns, I'll use Playwright's page.querySelectorAll() method with the same XPath selectors as in the Java code. Since querySelectorAll returns a Promise, I'll need to await the results. I'll store the counts of rows and columns in variables and print them using console.log.

I'll structure the code into a class with a @BeforeMethod that initializes the browser, context, and page. The test method will perform the navigation and element counting. I'll make sure to use async/await for all asynchronous operations.

Finally, I'll include the necessary imports at the top, such as expect from Playwright and the TestNG annotations. I'll also add comments to explain each step, keeping the code clean and understandable.

I should test the code to ensure it runs without errors and that the counts are correctly logged. This should replicate the functionality of the original Java code but using Playwright's TypeScript API.
</think>

```playwright
import { chromium } from 'playwright';
import { expect } from '@playwright/test';
import { BeforeMethod, Test } from 'testng-js';

export class WebTableHTML1 {
  private browser: chromium.Browser;
  private context: chromium.BrowserContext;
  private page: chromium.Page;

  @BeforeMethod()
  async setUp() {
    // Initialize browser and context
    this.browser = await chromium.launch();
    this.context = await this.browser.newContext({
      baseURL: 'https://html.com/tags/table/',
    });
    this.page = await this.context.newPage();
    // Set viewport size to maximize the window
    await this.page.setViewportSize({ width: 1920, height: 1080 });
    // Set default navigation timeout
    this.context.setDefaultNavigationTimeout(30000);
  }

  @Test()
  async countTableRowsAndColumns() {
    // Navigate to the base URL
    await this.page.goto('/');
    
    // Get all rows using XPath selector
    const rows = await this.page.querySelectorAll('//table//tr');
    const rowCount = rows.length;
    console.log(`Number of Rows: ${rowCount}`);

    // Get all columns using XPath selector
    const columns = await this.page.querySelectorAll('//table//tr//th');
    const columnCount = columns.length;
    console.log(`Number of Columns: ${columnCount}`);
  }
}
```