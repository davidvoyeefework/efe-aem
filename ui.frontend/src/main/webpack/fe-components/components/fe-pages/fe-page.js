import { getCookie, getFetch, handleLoader, fetchData, postJSONforKeys, pushToWindowObject } from "../../site/js/helper";
import {forKeysPayload} from './forkeys-payload';
export default class FePage {
    constructor() {
        document.addEventListener(
            "DOMContentLoaded",
            () => {
                window.aemfe = {};
                this.init();
            },
            { once: true }
        );
    }
    init() {
        const el = document.querySelector('.fe-load-data');
        if (!el) {
            return
        }
        handleLoader(true)
        this.fetchSponsorData();
        this.fetchAggregateData();
        this.getKeys();
    }
    async fetchSponsorData() {
        handleLoader(true)
        const apiHost = document.querySelector('#fe-properties')?.getAttribute('data-frame-api');//'http://localhost:3000';//document.querySelector('.sponsor-header').getAttribute('data-page-frame-api');
        const headers = {
            'Accept': 'application/json, text/plain, */*',
            'X-Spa-Name': 'landing-flow',
        };
        await fetchData(apiHost, headers).then(data => {
            handleLoader(false);
            if (data) {
                pushToWindowObject(data);
                const dataFromApi = new CustomEvent("messageFromfePage", {
                    messageFromParent: {
                    success: "true",
                    },
                });
                document.dispatchEvent(dataFromApi);
                handleLoader(false);
            }
        }).catch(error => {
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    async fetchAggregateData() {
        const daVarsStr = getCookie('daVars');
        const daVars = daVarsStr ? JSON.parse(decodeURIComponent(daVarsStr)) : null;
        if (!daVars || !daVars.sponsorId) {
            return
        }
        let apiUrl = document.querySelector('#fe-properties')?.getAttribute('data-aggregate-api');
        let apiHost = apiUrl.replace('{Recordkeeper}', daVars.sponsorId);
        const headers = {
            'Accept': 'application/json, text/plain, */*',
        }
        await fetchData(apiHost, headers).then(data => {
            console.log(data);
            pushToWindowObject(data);
            handleLoader(false);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    async getKeys() {
        let apiUrl = document.querySelector('#fe-properties')?.getAttribute('data-keys-api');
        const keys = forKeysPayload;
        await postJSONforKeys('https://www.feitest.com/api/v1/texts/forKeys', keys).then(data => {
            pushToWindowObject(data);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
}