package com.itssinghankit.stockex.domain.use_case.search

import com.itssinghankit.stockex.domain.repository.RepositoryInterface
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repositoryInterface: RepositoryInterface
) {
    suspend operator fun invoke(searchQuery: String) = repositoryInterface.search(searchQuery)

}