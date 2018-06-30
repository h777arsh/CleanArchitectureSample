package com.example.domain.interactor.browse

import com.example.domain.executor.PostExecutionThread
import com.example.domain.interactor.ObserverUseCase
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetProjects @Inject constructor(
        private val projectRepository: ProjectRepository,
        postExecutionThread: PostExecutionThread)
    : ObserverUseCase<List<Project>, Nothing>(postExecutionThread) {

    override fun buildUseCaseObserable(params: Nothing?): Observable<List<Project>> {
        return projectRepository.getProjects()
    }
}