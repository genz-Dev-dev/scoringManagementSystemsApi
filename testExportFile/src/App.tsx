import type React from "react"
import { api } from "./api/api";

const App: React.FC = () => {

  const handleAddFile = (e: React.ChangeEvent<HTMLInputElement>): void => {
    console.log(e.target.files?.[0])
  }

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    try {
      const response = await api.get("/students/export-student", {
        responseType: "blob"
      });
      if (response.status === 200) {
        const url = URL.createObjectURL(new Blob([response.data]));
        console.log(url)

        const link = document.createElement("a");
        link.href = url;

        let filename = "students.xlsx";
        const disposition = response.headers["content-disposition"];
        console.log(disposition)
        if (disposition && disposition.includes("filename=")) {
          filename = disposition.split("filename=")[1].replace(/"/g, "");
        }

        link.setAttribute("download", filename);
        document.body.appendChild(link);
        link.click();


        //clean up
        link.remove();
        URL.revokeObjectURL(url);
      }
    } catch (e) {

    } finally {

    }
  }

  return (
    <main className="h-screen overflow-x-hidden">
      <div className="mx-auto container px-4">
        <button
          onClick={handleSubmit}
          className="mt-4 ounded-md border border-gray-200 px-3 py-2 font-medium text-gray-700 transition-colors hover:bg-gray-50 hover:text-gray-900 focus:z-10 focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 focus:ring-offset-white focus:outline-none disabled:pointer-events-auto disabled:opacity-50">
          Export Student
        </button>
        <div className="mt-8 max-w-xl text-center mx-auto border border-slate-200 rounded-md p-6">
          <input
            onChange={handleAddFile}
            type="file" className="px-2.5 py-1 border border-slate-200 rounded-md" />
        </div>
      </div>
    </main>
  )
}

export default App