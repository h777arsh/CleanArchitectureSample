package com.example.domain.interactor.bookmark

import com.example.domain.executor.PostExecutionThread
import com.example.domain.interactor.CompletableUseCase
import com.example.domain.repository.ProjectRepository
import io.reactivex.Completable
import javax.inject.Inject

class UnbookmarkProject @Inject constructor(
        private val projectRepository: ProjectRepository,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCase<UnbookmarkProject.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can'be null!")
        return projectRepository.unBookmarkProject(params.projectId)
    }

    data class Params constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }

}