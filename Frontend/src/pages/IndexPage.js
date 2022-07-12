import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import IndexClient from "../api/indexClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class IndexPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
        document.getElementById('create-task').addEventListener('submit', this.onCreate);
        this.client = new IndexClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("create-task");

        const task = this.dataStore.get("task");

        if (task) {
            resultArea.innerHTML = `
                <div>ID: ${task.id}</div>
                <div>Name: ${task.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("id-field").value;
        this.dataStore.set("task", null);

        let result = await this.client.getTask(id, this.errorHandler);
        this.dataStore.set("task", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("task", null);

        let name = document.getElementById("taskInputName").value;

        const createdTask = await this.client.createTask(name, this.errorHandler);
        this.dataStore.set("task", createdTask);

        if (createdTask) {
            this.showMessage(`Created ${createdTask.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const examplePage = new IndexPage();
    examplePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
