package com.itssinghankit.stockex.domain.use_case.details

import com.itssinghankit.stockex.domain.repository.RepositoryInterface
import javax.inject.Inject

class GetStockPricePercentDetailsUseCase @Inject constructor(
    private val repositoryInterface: RepositoryInterface
){
    suspend operator fun invoke(symbol: String)=repositoryInterface.getPricePercentDetails(symbol)
}