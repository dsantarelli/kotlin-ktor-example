package handlers

interface RequestHandler<in TRequest, out TResponse> {
    fun handle(request: TRequest): TResponse
}