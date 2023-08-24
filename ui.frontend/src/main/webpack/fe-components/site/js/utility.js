export const availableThroughText = (key, value) => {
    let availableThroughText = value;
    if(availableThroughText) {
        availableThroughText = availableThroughText.replace(
            "{{ sponsor }}",
            window.aemfe?.preferredName
          );
        availableThroughText = availableThroughText.replace(
            "{{ industryPercent }}",
            lowerThanIndustryPercentValue()
          );
          availableThroughText = availableThroughText.replace(
            "{{ feePercent }}",
            (window.aemfe?.sponsoredFees[0]?.feeScheduleTiers[0].feeRate * 0.01).toPrecision(2) + "%"
          );
    }
    if (availableThroughText.slice(-2) === "**") {
        availableThroughText = availableThroughText.slice(0, -2);
      }
    return availableThroughText;
}
export const lowerThanIndustryPercentValue = ()=>{
    return window.aemfe?.sponsoredFees[0]?.lowerThanIndustryPercentValue;
}
export const promotionBannerFeeText = (key, value)=>{
    // let promotionBannerFeeText = value;
    // promotionBannerFeeText = this.pageData.promotionBannerFeeText.replace(
    //     "{{promoExpirationDate}}",
    //     this.pageData.promoExpirationDate
    //   );
}
export const PROMOTION_ANNOUNCED_END_DATE = (key, value)=>{
    console.log("sdksjfkfjdfk ==", value)
    let announcedDeadlineDate = value;
    let PROMOTION_ANNOUNCED_END_DATE;
    if (announcedDeadlineDate != null) {
        var announcedDeadlineDateArray = announcedDeadlineDate.split(
          "-"
        );
        if (
          announcedDeadlineDateArray.length > 0 &&
          Number(announcedDeadlineDateArray[1]) >= 3 &&
          Number(announcedDeadlineDateArray[1]) <= 7
        ) {
            PROMOTION_ANNOUNCED_END_DATE = new Date(
            announcedDeadlineDate
          ).toLocaleString("en-us", {
            month: "long",
            day: "numeric",
            year: "numeric"
          });
        } else {
            PROMOTION_ANNOUNCED_END_DATE = new Date(
            announcedDeadlineDate
          )
            .toLocaleString("en-us", {
              month: "short",
              day: "numeric",
              year: "numeric"
            })
            .replace(" ", ". ");
        }
    }
    return PROMOTION_ANNOUNCED_END_DATE;
}