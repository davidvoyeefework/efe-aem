import FeeModal from "../fe-form-fee-modal.hbs";

export default {
    title: "FE Components/form modal",
    argTypes: {
        variation: {
            options: ["Default", "theme-vanguard" , "theme-aon", "theme-ssga"],
            control: { type: "radio" },
        },
    },
};

const TemplateFormFeeModal = ({ label, ...args }) => FeeModal({...args});
export const FormFeeModal = TemplateFormFeeModal.bind();

