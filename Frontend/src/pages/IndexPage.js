import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import IndexClient from "../api/indexClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class IndexPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'onGet', 'renderExample', 'onDelete'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the task list.
     */
    async mount() {
        // document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
        console.log("In the mount")
        document.getElementById('create-task').addEventListener('submit', this.onCreate);
        this.client = new IndexClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        const allTasks = await this.client.getAllTasks(this.errorHandler);

        allTasks.forEach( function(item) {

            const str = item.dateAdded;
            const [month, day, year] = str.split(' ');
            const date = month + " " +  day + " " + year;

            const listObj = document.createElement("li");
            listObj.innerHTML = `
                <li class="list-group-item d-flex w-100 justify-content-between" id="delete-task">
                    <h5 class="mb-1">${item.name}</h5>
                    <small class="text-muted">${date}</small>
                    <button type="submit" class="btn">Edit</button>
                    <button type="click" class="btn" id="deleteButton">Delete</button>
                </li>
            `
            document.getElementById("resultOfList").appendChild(listObj);
        });

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
        console.log(event)
        console.log("We hit the onCreate")
        event.preventDefault();

        let name = document.getElementById("taskInputName").value;

        const createdTask = await this.client.createTask(name, this.errorHandler);
        this.dataStore.set("task", createdTask);

        if (createdTask) {
            this.showMessage(`Created ${createdTask.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }

    async onDelete(event) {
        console.log("onDelete is hit")
        event.preventDefault();
        await this.client.deleteTask(taskId, this.errorHandler);
        await this.renderExample();
        this.showMessage(`Deleted task ${taskId}!`);
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const indexPage = new IndexPage();
    console.log("We hit main")
    indexPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
