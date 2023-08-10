import Handlebars from "handlebars/runtime.js";
import FeMembershipDashboardButton from "../fe-membership-dashboard-button.hbs";

Handlebars.registerPartial("FeMembershipDashboardButton", FeMembershipDashboardButton);
export default {
    title: "FE Components/FeMembershipDashboardButton",
    argTypes: {
    },
};

export { FeMembershipDashboardButton as Default };
