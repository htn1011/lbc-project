import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class IndexClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getTask', 'createTask'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    async getTask(id, errorCallback) {
        try {
            const response = await this.client.get(`/task/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getConcert", error, errorCallback)
        }
    }

    async getAllTasks(errorCallback) {
        try {
            const response = await this.client.get(`/task`);
            console.log(response.data);
            return response.data;
        } catch (error) {
            this.handleError("getAllUsers", error, errorCallback)
        }
    }

    async createTask(name, errorCallback) {
        try {
            const response = await this.client.post(`/task`, {
                id: "id",
                name: name,
                dateAdded: new Date().toString()
            });
            return response.data;
        } catch (error) {
            this.handleError("createTask", error, errorCallback);
        }
    }

    async deleteTask(taskId, errorCallback) {
        try {
            await this.client.delete(`/task/${taskId}`);
        } catch (error) {
            this.handleError("deleteTask", error, errorCallback);
        }
    }

    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}
