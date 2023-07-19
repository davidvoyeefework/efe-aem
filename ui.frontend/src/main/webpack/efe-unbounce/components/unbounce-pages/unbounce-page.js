//import {handleLoader} from '../../site/js/helper'
import { getCookie, getFetch, handleLoader, fetchData } from "../../site/js/helper";
export default class UnbouncePage {
    constructor() {
        this.init()
    }
    init() {
        //handleLoader(true)
        // this.fetchAggregateData();
        // this.getAuthenticationStatus();
    }
    async fetchAggregateData() {
        const daVarsStr = getCookie('daVars');
        const daVars = daVarsStr ? JSON.parse(decodeURIComponent(daVarsStr)) : null;
        if (!daVars || !daVars.sponsorId) {
            return
        }
        let apiUrl = document.querySelector('#unbounce-properties')?.getAttribute('data-aggregate-api');
        let apiHost = apiUrl.replace('{Recordkeeper}', daVars.sponsorId);
        const headers = {
            'Accept': 'application/json, text/plain, */*',
        }
        await fetchData(apiHost, headers).then(data => {
            console.log(data);
            handleLoader(false);
            // this.loader.style.display = 'none';
            // if(data) {
            //     this.changeHeaderValues(data);
            // }
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    async getAuthenticationStatus() {
        const headers = {
            'Accept': 'application/json, text/plain, */*',
        }
        await fetchData('https://www.feitest.com/api/v1/userlogin/authenticationstatus', headers).then(data => {
            console.log(data);
            handleLoader(false);
            //if (data.isSponsorIdentified) {
                document.querySelector('.unbounce-form').style.display = 'block';
           // }
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
 }