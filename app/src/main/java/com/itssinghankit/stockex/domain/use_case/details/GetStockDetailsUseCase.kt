package com.itssinghankit.stockex.domain.use_case.details

import com.itssinghankit.stockex.domain.repository.RepositoryInterface
import javax.inject.Inject

class GetStockDetailsUseCase @Inject constructor(
    private val repositoryInterface: RepositoryInterface
){
    suspend operator fun invoke(symbol: String)=repositoryInterface.getDetails(symbol)
}