package com.example.manuel.baseproject.domain

import com.example.manuel.baseproject.domain.model.BeerModel

object BeersGenerator {

    fun getUnsortedBeers() = listOf(
            BeerModel(
                    1,
                    "BeerNameOne",
                    "BeerTaglineOne",
                    "urlImageOne",
                    87.0
            ),
            BeerModel(
                    2,
                    "BeerNameTwo",
                    "BeerTaglineTwo",
                    "urlImageTwo",
                    5.0
            ),
            BeerModel(
                    3,
                    "BeerNameThree",
                    "BeerTaglineThree",
                    "urlImageThree",
                    2.0
            ),
            BeerModel(
                    4,
                    "BeerNameFour",
                    "BeerTaglineFour",
                    "urlImageFour",
                    63.0
            ),
            BeerModel(
                    5,
                    "BeerNameFive",
                    "BeerTaglineFive",
                    "urlImageFive",
                    18.0
            )
    )

    fun getSortedBeers() = listOf(
            BeerModel(
                    3,
                    "BeerNameThree",
                    "BeerTaglineThree",
                    "urlImageThree",
                    2.0
            ),
            BeerModel(
                    2,
                    "BeerNameTwo",
                    "BeerTaglineTwo",
                    "urlImageTwo",
                    5.0
            ),
            BeerModel(
                    5,
                    "BeerNameFive",
                    "BeerTaglineFive",
                    "urlImageFive",
                    18.0
            ),
            BeerModel(
                    4,
                    "BeerNameFour",
                    "BeerTaglineFour",
                    "urlImageFour",
                    63.0
            ),
            BeerModel(
                    1,
                    "BeerNameOne",
                    "BeerTaglineOne",
                    "urlImageOne",
                    87.0
            )
    )
}