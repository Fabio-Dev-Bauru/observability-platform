export default function DashboardPage() {
  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Dashboard</h1>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-lg font-semibold mb-2">Total Logs</h2>
          <p className="text-3xl font-bold text-primary-600">-</p>
        </div>
        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-lg font-semibold mb-2">Error Rate</h2>
          <p className="text-3xl font-bold text-red-600">-</p>
        </div>
        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-lg font-semibold mb-2">Active Alerts</h2>
          <p className="text-3xl font-bold text-yellow-600">-</p>
        </div>
      </div>
      <div className="bg-white p-6 rounded-lg shadow">
        <h2 className="text-lg font-semibold mb-4">Metrics Overview</h2>
        <p className="text-gray-500">Metrics visualization will be implemented here</p>
      </div>
    </div>
  )
}

