module.exports = {

  /**
   * Indicates if a given string is valid (i.e. not null and not empty)
   *
   * @param str The string to be tested
   */
  isStringValid: function(str) {
    return str && str.length != 0;
  },

  /**
   * Provide an alphabetical sort on products array (based on "name" attribute) because
   * Mongoose is not providing case-insensitive sort
   *
   * @param products The products to sort
   * @param asc A boolean indicating if the sort has to be ascendant or not
   */
  sortProductsAlpha: function(products, asc) {
    if (asc) {
      return products.sort(function(a, b) {
        let nameA = a["name"].toLowerCase();
        let nameB = b["name"].toLowerCase();
        if (nameA > nameB) {
          return 1;
        } else if (nameA < nameB) {
          return -1;
        }
        return 0;
      });
    } else {
      return products.sort(function(a, b) {
        let nameA = a["name"].toLowerCase();
        let nameB = b["name"].toLowerCase();
        if (nameA > nameB) {
          return -1;
        } else if (nameA < nameB) {
          return 1;
        }
        return 0;
      });
    }
  }
};
