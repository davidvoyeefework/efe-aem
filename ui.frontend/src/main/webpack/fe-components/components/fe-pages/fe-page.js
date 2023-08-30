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
        handleLoader(true);
        this.getAuthenticationStatus();
        this.getKeys();
    }
    async fetchSponsorData(userLoggedIn) {
        let pageFrameApiFlow = userLoggedIn?'wpilanding':'landing-flow';
        handleLoader(true)
        const apiHost = document.querySelector('#fe-properties')?.getAttribute('data-frame-api');//'http://localhost:3000';//document.querySelector('.sponsor-header').getAttribute('data-page-frame-api');
        const headers = {
            'Accept': 'application/json, text/plain, */*',
            'X-Spa-Name': pageFrameApiFlow,
        };
        await fetchData(apiHost+pageFrameApiFlow, headers).then(data => {
            if (data) {
                if(data?.context?.isFeChannel) {
                    document.querySelector('body').classList.add('fe-direct-sponsor');
                } else {
                    document.querySelector('body').classList.add('fe-subadvised-sponsor');
                }
                pushToWindowObject(data);
                this.fetchAggregateData();
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
            handleLoader(false);
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
            const dataFromApi = new CustomEvent("messageFromfePage", {
                messageFromParent: {
                success: "true",
                },
            });
            document.dispatchEvent(dataFromApi);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    async getKeys() {
        let apiUrl = document.querySelector('#fe-properties')?.getAttribute('data-keys-api');
        const keys = forKeysPayload;
        await postJSONforKeys(apiUrl, keys).then(data => {
            pushToWindowObject(data);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    async getAuthenticationStatus() {
        let apiUrl = document.querySelector('#fe-properties')?.getAttribute('data-authenticate-api');
        const headers = {
            'Accept': 'application/json, text/plain, */*',
        }
        await fetchData(apiUrl, headers).then(data => {
            console.log(data);
            pushToWindowObject(data);
            const userLoggedIn = data.userLoggedIn;
            this.fetchSponsorData(userLoggedIn);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
}